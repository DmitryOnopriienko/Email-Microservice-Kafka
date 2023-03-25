package com.kafka.emailmicroservice.service;

import com.kafka.emailmicroservice.messaging.EmailMessage;

public interface EmailProducerService {
  void produceEmail(EmailMessage message);
}
