package com.example.book_shops.service.cart;

import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.model.Cart;
import com.example.book_shops.repository.CartItemRepository;
import com.example.book_shops.repository.CartRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    /**
     * Retrieves a cart by its ID.
     * Updates the cart's total amount and ensures any pending updates are written to the database.
     *
     * @param id the ID of the cart
     * @return the Cart object
     */
    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cartRepository.flush(); // Ensure any pending updates are written
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    /**
     * Clears the cart by deleting all items and the cart itself.
     *
     * @param id the ID of the cart
     */
    @Transactional
    @Override
    public void clearCart(Long id) {
        // Fetch the cart and remove all items
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        // Delete the cart itself
        cartRepository.deleteById(id);
    }

    /**
     * Gets the total price of the items in the cart.
     *
     * @param id the ID of the cart
     * @return the total amount of the cart
     */
    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    /**
     * Initializes a new cart and returns its ID.
     * The ID is generated atomically.
     *
     * @return the ID of the newly created cart
     */
    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();
    }

    /**
     * Retrieves a cart by the user ID.
     *
     * @param userId the ID of the user
     * @return the Cart associated with the user
     */
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
