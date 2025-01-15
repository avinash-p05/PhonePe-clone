package com.phonepe.v1.PhonePe.dto;

import com.phonepe.v1.PhonePe.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class TransDTO {
    private Long id;
    private String transactionId;
    private UserDTO sender;
    private UserDTO receiver;
    private BigDecimal amount;
    private Transaction.TransactionType transactionType;
    private Transaction.TransactionStatus status = Transaction.TransactionStatus.PENDING;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InitiateRequest {
        private String recipientPhoneNumber;
        private BigDecimal amount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionResponse {
        private String transactionId;
        private String senderPhoneNumber;
        private String receiverPhoneNumber;
        private BigDecimal amount;
        private Transaction.TransactionType transactionType;
        private Transaction.TransactionStatus status;
        private LocalDateTime createdAt;
    }

}
