package com.phonepe.v1.PhonePe.services;
import com.phonepe.v1.PhonePe.dto.TransactionsListResponse;
import com.phonepe.v1.PhonePe.exceptions.*;
import com.phonepe.v1.PhonePe.exceptions.Transaction.InsufficientBalanceException;
import com.phonepe.v1.PhonePe.exceptions.Transaction.InvalidAmountException;
import com.phonepe.v1.PhonePe.exceptions.Transaction.RecipientNotFoundException;
import com.phonepe.v1.PhonePe.exceptions.Transaction.TransactionException;
import com.phonepe.v1.PhonePe.models.Transaction;
import com.phonepe.v1.PhonePe.models.User;
import com.phonepe.v1.PhonePe.repositories.TransactionRepository;
import com.phonepe.v1.PhonePe.utils.TransactionIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final TransactionIdGenerator transactionIdGenerator;

    @Autowired
    private RedisTemplate<String,List<TransactionsListResponse>> redisTemplate;

    private static final String cacheKey = "transactions:user:";

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public Transaction initiateMoneyTransfer(User sender, String recipientPhoneNumber, BigDecimal amount) {
        // Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException();
        }

        // Load and validate recipient
        User recipient = userService.loadUserByPhoneNumber(recipientPhoneNumber);
        if (recipient == null) {
            throw new RecipientNotFoundException();
        }

        // Check for self-transfer
        if (sender.getPhoneNumber().equals(recipientPhoneNumber)) {
            throw new TransactionException("Cannot transfer money to yourself");
        }

        // Lock and check sender's balance
        User lockedSender = userService.lockUserForUpdate(sender.getId());
        if (lockedSender.getWalletBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        // Create transaction with generated ID
        Transaction transaction = Transaction.builder()
                .transactionId(transactionIdGenerator.generateTransactionId())
                .amount(amount)
                .sender(sender)
                .receiver(recipient)
                .status(Transaction.TransactionStatus.PENDING)
                .transactionType(Transaction.TransactionType.SEND_MONEY)
                .build();

        transaction = transactionRepository.save(transaction);

        try {
            // Perform the transfer
            userService.deductFromWallet(sender.getId(), amount);
            userService.addToWallet(recipient.getId(), amount);

            // Update transaction status
            transaction.setStatus(Transaction.TransactionStatus.SUCCESS);
            redisTemplate.delete(cacheKey+recipient.getId());
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            transaction.setStatus(Transaction.TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new TransactionException("Failed to process transaction: " + e.getMessage());
        } finally {
            redisTemplate.delete(cacheKey+sender.getId());
        }
    }

    @Transactional
    public Transaction getTransactionById(Long Id) {
        return transactionRepository.findById(Id)
                .orElseThrow(() -> new TransactionException("Transaction not found"));
    }

    @Transactional
    public List<TransactionsListResponse> getTransactionsByUser(User user) {


        List<TransactionsListResponse> cachedTransactions = redisTemplate.opsForValue().get(cacheKey+user.getId());

        if (cachedTransactions != null) {
            return cachedTransactions;
        }

        Long userId = user.getId();
        List<TransactionsListResponse> transactions = transactionRepository.findTransactionsByUserId(userId);

        // Save the fetched transactions into Redis cache with a time-to-live (TTL)
        redisTemplate.opsForValue().set(cacheKey, transactions,Duration.ofHours(1));

        return transactions;
    }

}