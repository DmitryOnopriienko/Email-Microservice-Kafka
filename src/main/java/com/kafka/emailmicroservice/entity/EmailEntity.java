package com.kafka.emailmicroservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "email")
public class EmailEntity {

  @Id
  private String id;

  @Field(type = FieldType.Text)
  private String subject;

  @Field(type = FieldType.Text)
  private String receiverEmail;

  @Field(type = FieldType.Text)
  private String content;

  @Field(type = FieldType.Boolean)
  private boolean isSent;

  @Field(type = FieldType.Text)
  private String errorMessage;
}
