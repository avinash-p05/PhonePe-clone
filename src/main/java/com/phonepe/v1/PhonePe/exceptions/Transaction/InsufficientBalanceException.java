package com.phonepe.v1.PhonePe.exceptions.Transaction;

public class InsufficientBalanceException extends TransactionException {
    public InsufficientBalanceException() {
        super("Insufficient balance in wallet");
    }
}
