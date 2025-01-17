package com.phonepe.v1.PhonePe.controllers;

import com.phonepe.v1.PhonePe.dto.ApiResponse;
import com.phonepe.v1.PhonePe.dto.TransDTO;
import com.phonepe.v1.PhonePe.dto.TransactionsListResponse;
import com.phonepe.v1.PhonePe.exceptions.Transaction.TransactionException;
import com.phonepe.v1.PhonePe.models.PaymentNotification;
import com.phonepe.v1.PhonePe.models.Transaction;
import com.phonepe.v1.PhonePe.models.User;
import com.phonepe.v1.PhonePe.repositories.UserRepository;
import com.phonepe.v1.PhonePe.services.NotificationService;
import com.phonepe.v1.PhonePe.services.TransactionService;
import com.phonepe.v1.PhonePe.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransController {

    UserRepository UserRepository;

    UserService userService;

    TransactionService transactionService;

    NotificationService notificationService;

    TransController(TransactionService transactionService, UserRepository UserRepository, UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.UserRepository = UserRepository;
        this.transactionService = transactionService;
        this.notificationService = notificationService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<ApiResponse<TransDTO.TransactionResponse>> sendMoney(
            @AuthenticationPrincipal User user,
            @RequestBody TransDTO.InitiateRequest request) {

        try {
            // Initiate transaction
            Transaction transaction = transactionService.initiateMoneyTransfer(
                    user, request.getRecipientPhoneNumber(), request.getAmount());

            // Build transaction response
            TransDTO.TransactionResponse transactionResponse = TransDTO.TransactionResponse.builder()
                    .transactionId(transaction.getTransactionId())
                    .senderPhoneNumber(transaction.getSender().getPhoneNumber())
                    .receiverPhoneNumber(transaction.getReceiver().getPhoneNumber())
                    .amount(transaction.getAmount())
                    .transactionType(transaction.getTransactionType())
                    .status(transaction.getStatus())
                    .createdAt(transaction.getCreatedAt())
                    .build();

           PaymentNotification receiverNotification = new PaymentNotification(
                transaction.getReceiver().getId().toString(), transaction.getTransactionId(),
                "You have received a payment of " + transaction.getAmount(), transaction.getAmount(), Transaction.TransactionType.RECEIVE_MONEY, LocalDateTime.now()
           );

            notificationService.sendPaymentNotification(receiverNotification);

            PaymentNotification senderNotification = new PaymentNotification(
                    transaction.getSender().getId().toString(), transaction.getTransactionId(),
                    "You have made a payment of " + transaction.getAmount(), transaction.getAmount(), Transaction.TransactionType.SEND_MONEY, LocalDateTime.now()
            );

            notificationService.sendPaymentNotification(senderNotification);


            return ResponseEntity.ok(ApiResponse.success(transactionResponse, "Amount sent successfully"));

        } catch (TransactionException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), "400"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An unexpected error occurred: " + e.getMessage(), "500"));
        }
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<TransactionsListResponse>>> history(
            @AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(ApiResponse.success(transactionService.getTransactionsByUser(user), "Transaction history retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An unexpected error occurred: " + e.getMessage(), "500"));
        }
    }


}
