package com.example.emailservice.producer;

import com.example.emailservice.dto.EmailRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String queueName;

    public EmailProducer(RabbitTemplate rabbitTemplate, @Value("${app.queue-name}") String queueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
    }

    public void publish(EmailRequest request) {
        rabbitTemplate.convertAndSend(queueName, request);
    }
}
