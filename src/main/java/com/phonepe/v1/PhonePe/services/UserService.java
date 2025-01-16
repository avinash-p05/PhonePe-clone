package com.phonepe.v1.PhonePe.services;

import com.phonepe.v1.PhonePe.dto.AuthDTO.RegisterRequest;
import com.phonepe.v1.PhonePe.dto.UserDTO;
import com.phonepe.v1.PhonePe.exceptions.Transaction.InsufficientBalanceException;
import com.phonepe.v1.PhonePe.models.User;
import com.phonepe.v1.PhonePe.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(RegisterRequest request) {

        User user = new User();
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setWalletBalance(BigDecimal.ZERO);
        user.setRole(User.Role.USER);
        user.setStatus(User.Status.ACTIVE);
        user.setDeviceFingerPrint(request.getDeviceFingerPrint());

        return userRepository.save(user);
    }

    public User loadUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phoneNumber));
    }

    @Transactional
    public User updateUser(Long userId, UserDTO.UpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }

        return userRepository.save(user);
    }

    @Transactional
    public User addToWallet(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setWalletBalance(user.getWalletBalance().add(amount));
        return userRepository.save(user);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User lockUserForUpdate(Long userId) {
        return userRepository.findByIdWithLock(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public void deductFromWallet(Long userId, BigDecimal amount) {
        User user = userRepository.findByIdWithLock(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        BigDecimal newBalance = user.getWalletBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException();
        }

        user.setWalletBalance(newBalance);
        userRepository.save(user);
    }

}
