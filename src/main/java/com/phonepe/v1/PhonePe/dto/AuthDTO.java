package com.phonepe.v1.PhonePe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private String phoneNumber;
        private String email;
        private String password;
        private String name;
        private String deviceFingerPrint;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String phoneNumber;
        private String password;
        private String deviceFingerPrint;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private String token;
        private String phoneNumber;
        private String name;
        private String email;
        private String deviceFingerPrint;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ForgotPasswordResponse {
        private String OTP;
        private String Validity;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResetPasswordRequest {
        private String phoneNumber;
        private String email;
        private String otp;
        private String newPassword;
    }
}
