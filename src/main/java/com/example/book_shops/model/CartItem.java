package com.example.book_shops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the cart item

    private int quantity; // Quantity of the product in the cart

    private BigDecimal unitPrice; // Price per unit of the product

    private BigDecimal totalPrice; // Total price for this cart item (unitPrice * quantity)

    private Long version; // Version for optimistic locking

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // The product associated with this cart item

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart; // The cart that contains this item

    /**
     * Sets the total price for this cart item based on its quantity and unit price.
     */
    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
    }


}