package com.kafka.emailmicroservice.repository;

import com.kafka.emailmicroservice.entity.EmailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends CrudRepository<EmailEntity, String> {
  List<EmailEntity> findAllByIsSentIsFalse();
}
