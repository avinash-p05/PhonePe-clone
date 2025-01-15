package com.phonepe.v1.PhonePe.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class TransactionIdGenerator {

    private static final String PREFIX = "T";
    private static final int RANDOM_NUMBER_LENGTH = 10;
    private static final Random random = new Random();

    public String generateTransactionId() {
        LocalDateTime now = LocalDateTime.now();

        // Format date and time components
        String dateTime = now.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));

        // Generate random numbers
        StringBuilder randomNumbers = new StringBuilder();
        for (int i = 0; i < RANDOM_NUMBER_LENGTH; i++) {
            randomNumbers.append(random.nextInt(10));
        }

        // Combine all parts
        return PREFIX + dateTime + randomNumbers;
    }
}
