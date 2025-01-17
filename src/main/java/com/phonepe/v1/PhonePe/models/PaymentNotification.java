package com.phonepe.v1.PhonePe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentNotification {
    private String userId;
    private String transactionId;
    private String message;
    private BigDecimal amount;
    private Transaction.TransactionType type; // SENT or RECEIVED
    private LocalDateTime timestamp;
}
