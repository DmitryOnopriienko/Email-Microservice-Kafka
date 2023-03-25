package com.kafka.emailmicroservice.listener;

import com.kafka.emailmicroservice.messaging.EmailMessage;
import com.kafka.emailmicroservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

  private final EmailService emailService;

  @Autowired
  public EmailListener(EmailService emailService) {
    this.emailService = emailService;
  }

  @KafkaListener(topics = "${kafka.topic.sendEmail}")
  public void sendEmail(EmailMessage message) {
    emailService.sendEmailAndSave(message);
  }
}
