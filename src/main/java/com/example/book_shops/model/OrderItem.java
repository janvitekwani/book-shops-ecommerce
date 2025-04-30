package com.example.book_shops.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the order item

    private int quantity;  // Quantity of the product ordered

    private BigDecimal price;  // Price of the product at the time of order

    @ManyToOne
    @JoinColumn(name = "order_id")  // Each order item is associated with one order
    private Order order;  // The order that this item belongs to

    @ManyToOne
    @JoinColumn(name = "product_id")  // Each order item is associated with one product
    private Product product;  // The product being purchased

    // Constructor for creating a new order item
    public OrderItem(Order order, Product product, int quantity, BigDecimal price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}