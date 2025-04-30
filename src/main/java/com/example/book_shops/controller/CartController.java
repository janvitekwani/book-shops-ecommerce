package com.example.book_shops.controller;

import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.model.Cart;
import com.example.book_shops.response.ApiResponse;
import com.example.book_shops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;


/**
 * Controller to handle all operations related to Cart management.
 * Exposes REST endpoints to retrieve cart details, clear a cart,
 * and get the total price of items in the cart.
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    // Injected service to handle business logic for Cart operations
    private final ICartService cartService;

    /**
     * Get the details of a specific cart by ID.
     *
     * @param cartId The ID of the cart to retrieve.
     * @return ResponseEntity containing cart data and success message.
     *         Returns 404 if cart is not found.
     */
    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Clear all items from a specific cart.
     *
     * @param cartId The ID of the cart to be cleared.
     * @return ResponseEntity with success or error message.
     */
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear Cart Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Calculate the total price of all items in a specific cart.
     *
     * @param cartId The ID of the cart.
     * @return ResponseEntity containing total price.
     *         Returns 404 if cart is not found.
     */
    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
