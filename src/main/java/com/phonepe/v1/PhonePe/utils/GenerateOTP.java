package com.phonepe.v1.PhonePe.utils;

public class GenerateOTP {
    public static String generateOTP() {
        int randomPin = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(randomPin);
    }
}

