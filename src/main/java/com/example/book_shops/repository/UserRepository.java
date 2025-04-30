package com.example.book_shops.repository;

import com.example.book_shops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByEmail(String email);  // find by the existing email
}
