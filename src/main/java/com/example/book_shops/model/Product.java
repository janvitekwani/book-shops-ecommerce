package com.example.book_shops.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the product

    private String name;  // Name of the product

    private String brand;  // Brand of the product

    private String description;  // Description of the product

    private BigDecimal price;  // Price of the product

    private int inventory;  // Number of items available in stock

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "category_id")  // Each product belongs to one category
    private Category category;  // Category to which the product belongs

    @OneToMany(mappedBy ="product", cascade = CascadeType.ALL)
    private List<Image> images;  // List of images associated with the product

    // Constructor for creating a new product
    public Product(String name, String brand, String description, BigDecimal price, int inventory, Category category) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.category = category;
    }


}
