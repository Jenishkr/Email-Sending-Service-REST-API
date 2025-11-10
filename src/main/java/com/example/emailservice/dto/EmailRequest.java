package com.example.emailservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

public class EmailRequest {
    @NotBlank
    @Email
    private String to;

    @NotBlank
    private String subject;

    private String body; // optional: raw HTML
    private Map<String, String> templateVariables; // optional
    private String templateName; // optional
    private List<String> attachments; // base64 encoded files

    public EmailRequest() {}

    // getters and setters
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public Map<String, String> getTemplateVariables() { return templateVariables; }
    public void setTemplateVariables(Map<String, String> templateVariables) { this.templateVariables = templateVariables; }

    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }

    public List<String> getAttachments() { return attachments; }
    public void setAttachments(List<String> attachments) { this.attachments = attachments; }
}
