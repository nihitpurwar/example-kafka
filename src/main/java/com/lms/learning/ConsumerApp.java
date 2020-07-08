package com.lms.learning;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;


public class ConsumerApp {
  public static void main(String[] args) {
    runConsumer();
  }

  private static void runConsumer() {
    try (Consumer<Long, String> consumer = ConsumerProducerFactory.createConsumer()) {
      int numOfRecordsToProcess = 50;
      while (numOfRecordsToProcess > 0) {
        ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis(Long.MAX_VALUE));
        for (TopicPartition partition : records.partitions()) {
          List<ConsumerRecord<Long, String>> partitionRecords = records.records(partition);
          for (ConsumerRecord<Long, String> record : partitionRecords) {
            numOfRecordsToProcess--;
            System.out.println(
                "Record partition " + record.partition() + " -> " + record.offset() + ": " + record.value());
          }
          long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
          consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
        }
      }
    }
  }
}