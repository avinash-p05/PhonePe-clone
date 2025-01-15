package com.phonepe.v1.PhonePe.dto;

import com.phonepe.v1.PhonePe.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String phoneNumber;
    private String email;
    private String name;
    private BigDecimal walletBalance;
    private User.Status status;

    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .name(user.getName())
                .walletBalance(user.getWalletBalance())
                .status(user.getStatus())
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        private String email;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WalletRequest {
        private BigDecimal amount;
    }
}
