package com.phonepe.v1.PhonePe.repositories;

import com.phonepe.v1.PhonePe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
}

