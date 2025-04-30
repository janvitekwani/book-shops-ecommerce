package com.example.book_shops.controller;

import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.response.ApiResponse;
import com.example.book_shops.service.cart.ICartItemService;
import com.example.book_shops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Controller class for handling operations related to Cart Items.
 * It exposes endpoints for adding, removing, and updating items in the cart.
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    // Service to handle CartItem-related business logic
    private final ICartItemService cartItemService;

    // Service to manage Cart-related actions (e.g., initialization)
    private final ICartService cartService;

    /**
     * Adds a product to the cart. If cartId is not provided, a new cart is initialized.
     *
     * @param cartId   Optional cart ID. If null, a new cart will be created.
     * @param productId ID of the product to be added.
     * @param quantity  Quantity of the product.
     * @return ResponseEntity with success or error message.
     */
    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            if (cartId == null) {
                // Initialize a new cart if no cart ID is provided
                cartId = cartService.initializeNewCart();
            }

            // Add item to the specified or newly created cart
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success", null));

        } catch (ResourceNotFoundException e) {
            // If any resource (e.g., product) is not found
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Removes a specific item from a cart.
     *
     * @param cartId ID of the cart.
     * @param itemId ID of the item to be removed.
     * @return ResponseEntity with success or error message.
     */
    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));

        } catch (ResourceNotFoundException e) {
            // If cart or item is not found
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Updates the quantity of a specific item in a cart.
     *
     * @param cartId   ID of the cart.
     * @param itemId   ID of the item to be updated.
     * @param quantity New quantity to set.
     * @return ResponseEntity with success or error message.
     */
    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update Item Success", null));

        } catch (ResourceNotFoundException e) {
            // If cart or item is not found
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
