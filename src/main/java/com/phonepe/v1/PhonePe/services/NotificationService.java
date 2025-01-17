package com.phonepe.v1.PhonePe.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.phonepe.v1.PhonePe.models.PaymentNotification;
import com.phonepe.v1.PhonePe.models.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    private static final String TOPIC = "payment-notifications";

    private final KafkaTemplate<String, PaymentNotification> kafkaTemplate;

    public NotificationService(KafkaTemplate<String, PaymentNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Producer Method
    public void sendPaymentNotification(PaymentNotification notification) {
        try {
            kafkaTemplate.send(TOPIC, notification.getUserId(), notification);
            log.info("Payment notification sent for transaction: {}", notification.getTransactionId());
        } catch (Exception e) {
            log.error("Error sending notification: ", e);
            throw new RuntimeException("Failed to send notification", e);
        }
    }

    // Consumer Method
    @KafkaListener(topics = TOPIC, groupId = "notification-group")
    public void consumePaymentNotification(PaymentNotification notification) {
        log.info("Received notification from Kafka: {}", notification);

        try {
            // Create FCM message
            Message firebaseMessage = Message.builder()
                    .putData("type", notification.getType().toString())
                    .putData("amount", String.valueOf(notification.getAmount()))
                    .putData("message", notification.getMessage())
                    .putData("transactionId", notification.getTransactionId())
                    .setTopic("user-topic-" + notification.getUserId()) // Replace with topic or token logic
                    .build();

            // Send the message to FCM
            String response = FirebaseMessaging.getInstance().send(firebaseMessage);
            log.info("Notification sent to FCM: {}", response);
        } catch (Exception e) {
            log.error("Error sending notification to FCM", e);
        }
    }
}
