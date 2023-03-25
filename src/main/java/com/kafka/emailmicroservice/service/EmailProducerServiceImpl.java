package com.kafka.emailmicroservice.service;

import com.kafka.emailmicroservice.messaging.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducerServiceImpl implements EmailProducerService {

  private final KafkaTemplate<String, EmailMessage> kafkaTemplate;

  @Value("${kafka.topic.sendEmail}")
  private String emailTopic;

  @Autowired
  public EmailProducerServiceImpl(KafkaTemplate<String, EmailMessage> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void produceEmail(EmailMessage message) {
    kafkaTemplate.send(emailTopic, message);
  }
}
