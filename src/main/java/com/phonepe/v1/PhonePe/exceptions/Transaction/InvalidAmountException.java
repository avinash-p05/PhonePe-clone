package com.phonepe.v1.PhonePe.exceptions.Transaction;

public class InvalidAmountException extends TransactionException {
    public InvalidAmountException() {
        super("Amount should be greater than 0");
    }
}