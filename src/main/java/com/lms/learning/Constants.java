package com.lms.learning;

public interface Constants {
  String KAFKA_BROKERS = "localhost:9092";
  Integer NUMBER_OF_PRODUCED_RECORDS = 5;
  String TOPIC_NAME = "messages-pulled";
  String GROUP_ID_CONFIG = "consumerGroup";
}
