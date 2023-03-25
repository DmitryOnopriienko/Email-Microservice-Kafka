package com.kafka.emailmicroservice.controller;

import com.kafka.emailmicroservice.messaging.EmailMessage;
import com.kafka.emailmicroservice.service.EmailProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

  private final EmailProducerService emailProducerService;

  @Autowired
  public EmailController(EmailProducerService emailProducerService) {
    this.emailProducerService = emailProducerService;
  }

  @PostMapping("/send_email")
  public void sendEmail(@RequestBody EmailMessage message) {
    emailProducerService.produceEmail(message);
  }
}
