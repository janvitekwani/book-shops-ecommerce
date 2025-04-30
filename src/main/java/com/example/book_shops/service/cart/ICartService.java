package com.example.book_shops.service.cart;

import com.example.book_shops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    /**
     * Retrieves a cart by its ID.
     *
     * @param id the ID of the cart
     * @return the Cart object
     */
    Cart getCart(Long id);

    /**
     * Clears all items and the cart itself.
     *
     * @param id the ID of the cart
     */
    void clearCart(Long id);

    /**
     * Gets the total price of the cart.
     *
     * @param id the ID of the cart
     * @return the total amount of the cart
     */
    BigDecimal getTotalPrice(Long id);

    /**
     * Initializes a new cart and returns its ID.
     *
     * @return the ID of the newly created cart
     */
    Long initializeNewCart();

    /**
     * Retrieves a cart based on the user ID.
     *
     * @param userId the ID of the user
     * @return the Cart associated with the user
     */
    Cart getCartByUserId(Long userId);


}
