package com.kafka.emailmicroservice;

import com.kafka.emailmicroservice.entity.EmailEntity;
import com.kafka.emailmicroservice.messaging.EmailMessage;
import com.kafka.emailmicroservice.repository.EmailRepository;
import com.kafka.emailmicroservice.service.EmailService;
import com.kafka.emailmicroservice.service.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class EmailMicroserviceKafkaApplicationTests {

  @MockBean
  private EmailRepository emailRepository;

  @MockBean
  EmailService emailService;

  @MockBean
  private JavaMailSender mailSender;

  @BeforeEach
  public void setup() {
    emailService = new EmailServiceImpl(mailSender, emailRepository);
  }

  @Test
  public void sendEmailAndSave_shouldSaveEmailEntity() {

    doThrow(new MailSendException("error message"))
            .when(mailSender).send(Mockito.any(SimpleMailMessage.class));

    EmailMessage message = new EmailMessage("Test subject", "test@example.com", "Test content");
    EmailEntity entity = new EmailEntity();
    entity.setReceiverEmail("test@example.com");
    entity.setSubject("Test subject");
    entity.setContent("Test content");
    entity.setErrorMessage("org.springframework.mail.MailSendException:error message");

    emailService.sendEmailAndSave(message);

    verify(emailRepository, times(1)).save(entity);
  }
}
