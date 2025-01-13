package com.phonepe.v1.PhonePe.repositories;

import com.phonepe.v1.PhonePe.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderId(Long senderId);
    List<Transaction> findByReceiverId(Long receiverId);
    List<Transaction> findByTransactionType(Transaction.TransactionType type);
}

