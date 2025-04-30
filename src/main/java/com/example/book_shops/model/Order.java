package com.example.book_shops.model;

import com.example.book_shops.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



    @Getter
    @Setter
    @NoArgsConstructor
    @Entity
    @Table(name = "orders")
    public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long orderId;  // Unique identifier for the order

        private LocalDate orderDate;  // Date when the order was placed

        private BigDecimal totalAmount;  // Total amount for the order

        @Enumerated(EnumType.STRING)
        private OrderStatus orderStatus;  // Status of the order (e.g., pending, shipped)

        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<OrderItem> orderItems = new HashSet<>();  // Items in the order

        @ManyToOne
        @JoinColumn(name = "user_id")  // Each order is associated with one user
        private User user;  // User who placed the order
}