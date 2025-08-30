package org.castle.djames.streamflow.command.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@Slf4j
@RequestMapping("/v1")
@RestController
public class StreamFlowController {

    @GetMapping("/test")
    public ResponseEntity<?> test() {

        log.info("Test");

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:29092");
        properties.setProperty("security.protocol", "PLAINTEXT");
        properties.setProperty("schema-registry-url", "http://127.0.0.1:7081");
        properties.setProperty("sasl.mechanism", "SCRAM-SHA-256");
        properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String>  producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "Hello World!!!");

        producer.send(producerRecord);

        producer.close();


        return ResponseEntity.ok()
                .body("Test");
    }

}
