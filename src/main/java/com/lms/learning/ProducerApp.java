package com.lms.learning;

import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;


public class ProducerApp {
  public static void main(String[] args) {
		runProducer();
  }

  private static void runProducer() {
    Producer<Long, String> producer = ConsumerProducerFactory.createProducer();
    for (int index = 0; index < Constants.NUMBER_OF_PRODUCED_RECORDS; index++) {
      final ProducerRecord<Long, String> record = new ProducerRecord<>(Constants.TOPIC_NAME, "This is record " + index);
      try {
        RecordMetadata metadata = producer.send(record).get();
        System.out.println("Record sent with key " + index + " to partition " + metadata.partition() + " with offset "
            + metadata.offset());
      } catch (ExecutionException | InterruptedException e) {
        System.out.println("Error in sending record");
        e.printStackTrace();
      }
    }
  }
}