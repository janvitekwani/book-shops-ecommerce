package com.example.book_shops.request;
import com.example.book_shops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private Long id;  // Unique identifier for the product

    private String name;  // Name of the product

    private String brand;  // Brand of the product

    private BigDecimal price;  // Price of the product

    private int inventory;  // Available stock quantity

    private String description;  // Description of the product

    private Category category;  // Category to which the product belongs
}