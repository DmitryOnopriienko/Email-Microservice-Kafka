package com.kafka.emailmicroservice.service;

import com.kafka.emailmicroservice.entity.EmailEntity;
import com.kafka.emailmicroservice.messaging.EmailMessage;
import com.kafka.emailmicroservice.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  private final EmailRepository emailRepository;

  @Autowired
  public EmailServiceImpl(JavaMailSender mailSender, EmailRepository emailRepository) {
    this.mailSender = mailSender;
    this.emailRepository = emailRepository;
  }

  @Override
  public void sendEmailAndSave(EmailMessage message) {
    EmailEntity entity = mapEmailMessageToEntity(message);
    sendEmail(entity);
    emailRepository.save(entity);
  }

  private void sendEmail(EmailEntity entity) {

    SimpleMailMessage mailMessage = mapEmailEntityToSimpleMailMessage(entity);

    try {
      mailSender.send(mailMessage);

      entity.setSent(true);

    } catch (MailException e) {
      entity.setSent(false);
      entity.setErrorMessage(e.getClass().getName() + ":" + e.getMessage());

    }
  }

  @Scheduled(fixedRate = 300000)
  public void retrySending() {
    System.out.println("log:Scheduled:" + LocalDateTime.now());
    List<EmailEntity> entities = emailRepository.findAllByIsSentIsFalse();

    entities.forEach(entity -> {
      sendEmail(entity);
      emailRepository.save(entity);
    });
  }

  private SimpleMailMessage mapEmailEntityToSimpleMailMessage(EmailEntity emailEntity) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    mailMessage.setFrom("dmitry.onopriienko@gmail.com");
    mailMessage.setTo(emailEntity.getReceiverEmail());
    mailMessage.setSubject(emailEntity.getSubject());
    mailMessage.setText(emailEntity.getContent());

    return mailMessage;
  }

  private EmailEntity mapEmailMessageToEntity(EmailMessage message) {
    EmailEntity entity = new EmailEntity();

    entity.setReceiverEmail(message.getEmail());
    entity.setSubject(message.getSubject());
    entity.setContent(message.getContent());

    return entity;
  }
}
