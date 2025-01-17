package com.phonepe.v1.PhonePe.services;

import com.phonepe.v1.PhonePe.models.PaymentNotification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationConsumerService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationConsumerService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "payment-notifications", groupId = "notification-group")
    public void consume(PaymentNotification notification) {
        try {
            String destination = "/topic/notifications/" + notification.getUserId();
            messagingTemplate.convertAndSend(destination, notification);
            log.info("Notification forwarded to WebSocket for user: {}", notification.getUserId());
        } catch (Exception e) {
            log.error("Error processing notification: ", e);
        }
    }
}