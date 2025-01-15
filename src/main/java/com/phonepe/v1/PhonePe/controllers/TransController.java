package com.phonepe.v1.PhonePe.controllers;

import com.phonepe.v1.PhonePe.dto.ApiResponse;
import com.phonepe.v1.PhonePe.dto.TransDTO;
import com.phonepe.v1.PhonePe.models.Transaction;
import com.phonepe.v1.PhonePe.models.User;
import com.phonepe.v1.PhonePe.repositories.UserRepository;
import com.phonepe.v1.PhonePe.services.TransactionService;
import com.phonepe.v1.PhonePe.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransController {

    UserRepository UserRepository;

    TransactionService transactionService;

    TransController(TransactionService transactionService, UserRepository UserRepository) {
        this.UserRepository = UserRepository;
        this.transactionService = transactionService;;
    }

    @PostMapping("/initiate")
    public ResponseEntity<ApiResponse<TransDTO.TransactionResponse>> addMoney(@AuthenticationPrincipal User user, @RequestBody TransDTO.InitiateRequest request) {
//            if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
//                return ResponseEntity.badRequest().body(ApiResponse.validationError("Amount should be greater than 0","amount" ));
//            }
//            if(userService.loadUserByPhoneNumber(request.getRecipientPhoneNumber())==null) {
//                return ResponseEntity.badRequest().body(ApiResponse.error("Receiver doesn't exits already exists with this phone number","400"));
//            }
//            if(user.getWalletBalance().compareTo(request.getAmount()) < 0) {
//                return ResponseEntity.badRequest().body(ApiResponse.error("Insufficient balance","400"));
//            }

            Transaction transaction = transactionService.initiateMoneyTransfer(user, request.getRecipientPhoneNumber(), request.getAmount());

            TransDTO.TransactionResponse transactionResponse = TransDTO.TransactionResponse.builder()
                    .transactionId(transaction.getTransactionId())
                    .senderPhoneNumber(transaction.getSender().getPhoneNumber())
                    .receiverPhoneNumber(transaction.getReceiver().getPhoneNumber())
                    .amount(transaction.getAmount())
                    .transactionType(transaction.getTransactionType())
                    .status(transaction.getStatus())
                    .createdAt(transaction.getCreatedAt())
                    .build();

            return ResponseEntity.ok(ApiResponse.success(transactionResponse,"Amount sent Successfully"));

    }


}
