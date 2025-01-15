package com.phonepe.v1.PhonePe.controllers;

import com.phonepe.v1.PhonePe.configurations.JwtService;
import com.phonepe.v1.PhonePe.dto.ApiResponse;
import com.phonepe.v1.PhonePe.dto.AuthDTO.RegisterRequest;
import com.phonepe.v1.PhonePe.dto.AuthDTO.LoginRequest;
import com.phonepe.v1.PhonePe.dto.AuthDTO.AuthResponse;
import com.phonepe.v1.PhonePe.models.User;
import com.phonepe.v1.PhonePe.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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

            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .phoneNumber(user.getPhoneNumber())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();

            return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
        } catch (BadCredentialsException e) {
            throw e; // Will be handled by GlobalExceptionHandler
        }
    }
}