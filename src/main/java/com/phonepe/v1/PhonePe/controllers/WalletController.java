package com.phonepe.v1.PhonePe.controllers;

import com.phonepe.v1.PhonePe.dto.ApiResponse;
import com.phonepe.v1.PhonePe.dto.UserDTO;
import com.phonepe.v1.PhonePe.models.User;
import com.phonepe.v1.PhonePe.repositories.UserRepository;
import com.phonepe.v1.PhonePe.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping("/api/v1/wallet")
@RestController
public class WalletController {

    UserRepository userRepository;
    UserService userService;

    public WalletController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/add-money")
    public ResponseEntity<ApiResponse<String>> addMoney(
            @AuthenticationPrincipal User user,
            @RequestBody UserDTO.WalletRequest request) {
        try{
            if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Amount should be greater than 0", "400"));
            }

            userService.addToWallet(user.getId(), request.getAmount());

            return ResponseEntity.ok(ApiResponse.success("Amount added Successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Error adding money to wallet", "400"));
        }
    }

    @GetMapping("/check-balance")
    public ResponseEntity<ApiResponse<BigDecimal>> getWalletBalance(@AuthenticationPrincipal User user) {
        try{

            return ResponseEntity.ok(ApiResponse.success(user.getWalletBalance(),"Amount retrieved Successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Error adding money to wallet", "400"));
        }
    }

}
