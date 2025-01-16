package com.phonepe.v1.PhonePe.repositories;

import com.phonepe.v1.PhonePe.dto.TransDTO;
import com.phonepe.v1.PhonePe.dto.TransactionsListResponse;
import com.phonepe.v1.PhonePe.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderId(Long senderId);
    List<Transaction> findByReceiverId(Long receiverId);
    List<Transaction> findByTransactionType(Transaction.TransactionType type);


    @Query("""
    SELECT new com.phonepe.v1.PhonePe.dto.TransactionsListResponse(
        t.transactionId,\s
        s.phoneNumber,\s
        r.phoneNumber,\s
        t.amount,\s
        t.transactionType,\s
        t.status,\s
        t.createdAt
    )
    FROM Transaction t
    JOIN t.sender s
    JOIN t.receiver r
    WHERE s.id = :id OR r.id = :id
""")
    List<TransactionsListResponse> findTransactionsByUserId(Long id);

}
