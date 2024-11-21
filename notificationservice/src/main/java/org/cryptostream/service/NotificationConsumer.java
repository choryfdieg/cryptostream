package org.cryptostream.service;

import org.cryptostream.model.entity.Notification;
import org.cryptostream.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    
    @Autowired
    private NotificationRepository notificacionRepository;
    
    @KafkaListener(id = "notification-listener", topics = "cryptostream-topic",
        autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:3}")
    public void listen(String data) {
        Notification notification = Notification.builder().message(data).build();
    
        notificacionRepository.save(notification);
    }
}