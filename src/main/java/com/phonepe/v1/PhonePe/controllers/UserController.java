package com.phonepe.v1.PhonePe.controllers;

import com.phonepe.v1.PhonePe.models.User;
import com.phonepe.v1.PhonePe.repositories.UserRepository;
import com.phonepe.v1.PhonePe.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    UserRepository userRepository;

    @Autowired
    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createNewUser(@RequestBody String phoneNumber, String email, String password, String name) {
        try {
            Map<String, Object> response = new HashMap<>();
            if (Objects.isNull(phoneNumber) || Objects.isNull(email) || Objects.isNull(password) || Objects.isNull(name)) {
                response.put("status", "invalid");
                response.put("message", "Invalid request");
                return ResponseEntity.badRequest().body(response);
            }

            if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
                response.put("status", "false");
                response.put("message", "User with phone number already exists");
                return ResponseEntity.badRequest().body(response);
            }

            String hashedPassword = PasswordUtil.hashPassword(password);

            User user = new User();
            user.setPhoneNumber(phoneNumber);
            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setName(name);

            response.put("status", "success");
            response.put("message", "User created successfully");
            response.put("data", user);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Internal server error");
            return ResponseEntity.internalServerError().body(response);
        }
    }


}
