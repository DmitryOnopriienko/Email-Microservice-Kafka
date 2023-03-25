package com.kafka.emailmicroservice.service;

import com.kafka.emailmicroservice.messaging.EmailMessage;

public interface EmailService {
  void sendEmailAndSave(EmailMessage message);
}
