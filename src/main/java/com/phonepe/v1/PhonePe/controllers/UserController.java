package com.phonepe.v1.PhonePe.controllers;

import com.phonepe.v1.PhonePe.dto.ApiResponse;
import com.phonepe.v1.PhonePe.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.phonepe.v1.PhonePe.dto.UserDTO;
import com.phonepe.v1.PhonePe.services.UserService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UserDTO.UpdateRequest request) {
        User updatedUser = userService.updateUser(user.getId(), request);
        return ResponseEntity.ok(UserDTO.fromUser(updatedUser));
    }

}
