package com.phonepe.v1.PhonePe.exceptions.Transaction;

public class RecipientNotFoundException extends TransactionException {
    public RecipientNotFoundException() {
        super("Recipient not found");
    }
}
