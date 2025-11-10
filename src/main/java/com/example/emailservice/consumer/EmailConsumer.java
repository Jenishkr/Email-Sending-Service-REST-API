package com.example.emailservice.consumer;

import com.example.emailservice.dto.EmailRequest;
import com.example.emailservice.entity.EmailEntity;
import com.example.emailservice.entity.EmailStatus;
import com.example.emailservice.repository.EmailRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Base64;

@Service
public class EmailConsumer {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private TemplateEngine templateEngine;

    private static final int MAX_RETRY = 5;

    @RabbitListener(queues = "${app.queue-name}")
    public void handleEmail(EmailRequest request) {
        EmailEntity entity = new EmailEntity();
        entity.setToEmail(request.getTo());
        entity.setSubject(request.getSubject());
        entity.setBody(request.getBody());
        entity.setStatus(EmailStatus.QUEUED);
        emailRepository.save(entity);

        int attempt = 0;
        boolean sent = false;

        while (attempt < MAX_RETRY && !sent) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(request.getTo());
                helper.setSubject(request.getSubject());

                String body = request.getBody();
                if (request.getTemplateName() != null && request.getTemplateVariables() != null) {
                    Context context = new Context();
                    request.getTemplateVariables().forEach(context::setVariable);
                    body = templateEngine.process(request.getTemplateName(), context);
                }
                helper.setText(body == null ? "" : body, true);
                helper.setFrom(System.getProperty("spring.mail.username", "noreply@example.com"));

                // Handle attachments
                if (request.getAttachments() != null) {
                    for (int i = 0; i < request.getAttachments().size(); i++) {
                        String base64 = request.getAttachments().get(i);
                        byte[] data = Base64.getDecoder().decode(base64);
                        helper.addAttachment("attachment_" + (i + 1), () -> new java.io.ByteArrayInputStream(data));
                    }
                }

                mailSender.send(message);

                entity.setStatus(EmailStatus.SENT);
                sent = true;
            } catch (Exception e) {
                attempt++;
                entity.setRetryCount(attempt);
                entity.setStatus(EmailStatus.FAILED);
                System.err.println("Attempt " + attempt + " failed: " + e.getMessage());
                try {
                    long backoff = (long) Math.pow(2, attempt) * 1000L; // exponential backoff delay
                    Thread.sleep(backoff);
                } catch (InterruptedException ignored) {}
            }
        }

        emailRepository.save(entity);

        if (!sent) {
            System.err.println("❌ Email delivery failed after max retries for: " + request.getTo());
        } else {
            System.out.println("✅ Email sent successfully to: " + request.getTo());
        }
    }
}
