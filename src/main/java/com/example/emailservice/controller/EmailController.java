package com.example.emailservice.controller;

import com.example.emailservice.dto.EmailRequest;
import com.example.emailservice.producer.EmailProducer;
import com.example.emailservice.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailProducer producer;

    @Autowired
    private EmailRepository repository;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody EmailRequest request) {
        producer.publish(request);
        return ResponseEntity.ok("Queued");
    }

    @GetMapping("/logs")
    public ResponseEntity<List> logs() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> status(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
