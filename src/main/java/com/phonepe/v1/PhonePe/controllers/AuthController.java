package com.phonepe.v1.PhonePe.controllers;

import com.phonepe.v1.PhonePe.configurations.JwtService;
import com.phonepe.v1.PhonePe.dto.ApiResponse;
import com.phonepe.v1.PhonePe.dto.AuthDTO;
import com.phonepe.v1.PhonePe.dto.AuthDTO.RegisterRequest;
import com.phonepe.v1.PhonePe.dto.AuthDTO.LoginRequest;
import com.phonepe.v1.PhonePe.dto.AuthDTO.AuthResponse;
import com.phonepe.v1.PhonePe.models.User;
import com.phonepe.v1.PhonePe.repositories.UserRepository;
import com.phonepe.v1.PhonePe.services.UserService;
import com.phonepe.v1.PhonePe.utils.GenerateOTP;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {

        try {

            if(userService.loadUserByPhoneNumber(request.getPhoneNumber()) != null) {
                return ResponseEntity.badRequest().body(ApiResponse.error("User already exists with this phone number","400"));
            }

            User user = userService.registerUser(request);
            String token = jwtService.generateToken(user);

            AuthResponse authResponse = AuthResponse.builder()
                            .token(token)
                            .phoneNumber(user.getPhoneNumber())
                            .name(user.getName())
                            .email(user.getEmail())
                            .build();
            return ResponseEntity.ok(ApiResponse.success(authResponse,"User registered successfully"));

        } catch (BadCredentialsException e) {
            throw e;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        try {

            if(userService.loadUserByPhoneNumber(request.getPhoneNumber()) == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error("User doesn't exists with this phone number","400"));
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhoneNumber(),
                            request.getPassword()
                    )
            );

            User user = userService.loadUserByPhoneNumber(request.getPhoneNumber());
            String token = jwtService.generateToken(user);

            user.setDeviceFingerPrint(request.getDeviceFingerPrint());
            userRepository.save(user);

            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .phoneNumber(user.getPhoneNumber())
                    .name(user.getName())
                    .email(user.getEmail())
                    .deviceFingerPrint(user.getDeviceFingerPrint())
                    .build();



            return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
        } catch (BadCredentialsException e) {
            throw e; // Will be handled by GlobalExceptionHandler
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestHeader("Authorization") String token) {
        try {
            String phoneNumber = jwtService.extractUsername(token);
            User user = userService.loadUserByPhoneNumber(phoneNumber);
            String newToken = jwtService.generateToken(user);

            AuthResponse response = AuthResponse.builder()
                    .token(newToken)
                    .phoneNumber(user.getPhoneNumber())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();

            return ResponseEntity.ok(ApiResponse.success(response, "Token refreshed successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Error refreshing token", "400"));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<AuthDTO.ForgotPasswordResponse>> forgotPassword(@RequestBody Map<String, String> request) {
        try {
            // Extract phoneNumber from request body
            String phoneNumber = request.get("phoneNumber");

            if (phoneNumber == null || phoneNumber.isEmpty()) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Phone number is required", "400"));
            }

            // Load user by phone number
            User user = userService.loadUserByPhoneNumber(phoneNumber);

            // Check if user exists
            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("User not found with phone number: " + phoneNumber, "400"));
            }

            // Generate OTP
            String otp = GenerateOTP.generateOTP();
            String message = "Your OTP is: " + otp;

            // Set OTP and validity
            Date currentDate = new Date();
            long validityInMillis = currentDate.getTime() + 300000; // 5 minutes
            Date otpValidity = new Date(validityInMillis);
            user.setOtp(otp);
            user.setOtpValidity(otpValidity);

            // Save user with updated OTP details
            userRepository.save(user);

            // Build response
            AuthDTO.ForgotPasswordResponse response = AuthDTO.ForgotPasswordResponse.builder()
                    .OTP(otp)
                    .Validity("5 minutes")
                    .build();

            return ResponseEntity.ok(ApiResponse.success(response, "OTP sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error sending OTP: " + e.getMessage(), "500"));
        }
    }

}