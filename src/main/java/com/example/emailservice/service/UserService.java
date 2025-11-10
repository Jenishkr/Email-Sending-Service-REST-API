package com.example.emailservice.service;

import com.example.emailservice.dto.EmailRequest;
import com.example.emailservice.dto.RegisterRequest;
import com.example.emailservice.entity.User;
import com.example.emailservice.producer.EmailProducer;
import com.example.emailservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailProducer emailProducer;

    public User register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user = userRepository.save(user);

        // Send welcome email
        sendWelcomeEmail(user);

        return user;
    }

    private void sendWelcomeEmail(User user) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Welcome to Email Service");
        emailRequest.setTemplateName("welcome-email");
        Map<String, String> variables = new HashMap<>();
        variables.put("name", user.getUsername());
        variables.put("username", user.getUsername());
        variables.put("email", user.getEmail());
        emailRequest.setTemplateVariables(variables);
        emailProducer.publish(emailRequest);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
