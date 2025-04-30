package com.example.book_shops.service.cart;

import com.example.book_shops.model.CartItem;

public interface ICartItemService {
    /**
     * Adds an item to the cart.
     *
     * @param cartId    the ID of the cart
     * @param productId the ID of the product
     * @param quantity  the quantity of the product
     */
    void addItemToCart(Long cartId, Long productId, int quantity);

    /**
     * Removes an item from the cart.
     *
     * @param cartId    the ID of the cart
     * @param productId the ID of the product
     */
    void removeItemFromCart(Long cartId, Long productId);

    /**
     * Updates the quantity of an item in the cart.
     *
     * @param cartId    the ID of the cart
     * @param productId the ID of the product
     * @param quantity  the new quantity of the product
     */
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    /**
     * Retrieves a CartItem from the cart based on the product ID.
     *
     * @param cartId    the ID of the cart
     * @param productId the ID of the product
     * @return the CartItem associated with the product ID
     */
    CartItem getCartItem(Long cartId, Long productId);
}
