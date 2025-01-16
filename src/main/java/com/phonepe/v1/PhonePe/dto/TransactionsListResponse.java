package com.phonepe.v1.PhonePe.dto;

import com.phonepe.v1.PhonePe.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsListResponse {
    private String transactionId;
    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private BigDecimal amount;
    private Transaction.TransactionType transactionType;
    private Transaction.TransactionStatus status;
    private LocalDateTime createdAt;
}

