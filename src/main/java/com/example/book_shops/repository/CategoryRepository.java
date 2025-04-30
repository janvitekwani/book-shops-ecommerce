package com.example.book_shops.repository;

import com.example.book_shops.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);  // find the user by name

    boolean existsByName(String name);  // find the existby name
}
