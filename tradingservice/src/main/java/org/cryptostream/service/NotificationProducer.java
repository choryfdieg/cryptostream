package org.cryptostream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public NotificationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(String message) {
        
        kafkaTemplate.send("cryptostream-topic", message);
    }
}