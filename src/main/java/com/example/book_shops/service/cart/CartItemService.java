package com.example.book_shops.service.cart;

import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.model.Cart;
import com.example.book_shops.model.CartItem;
import com.example.book_shops.model.Product;
import com.example.book_shops.repository.CartItemRepository;
import com.example.book_shops.repository.CartRepository;
import com.example.book_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService  implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    /**
     * Adds a product item to the cart.
     * If the product already exists in the cart, it updates the quantity and total price.
     * Otherwise, it adds a new item to the cart.
     *
     * @param cartId    the ID of the cart
     * @param productId the ID of the product to be added
     * @param quantity  the quantity of the product to be added
     */
    @Transactional
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // Fetch the cart and product objects from the database
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);

        // Check if the product already exists in the cart
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // If the item exists, update the quantity and recalculate the total price
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setTotalPrice();
        } else {
            // If the item doesn't exist, create a new CartItem and add it to the cart
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(product.getPrice());
            newItem.setTotalPrice();
            cart.getItems().add(newItem);
        }

        // Save the updated cart
        cartRepository.save(cart);
    }

    /**
     * Removes a product item from the cart.
     *
     * @param cartId    the ID of the cart
     * @param productId the ID of the product to be removed
     */
    @Transactional
    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        // Get the cart and the item to remove
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);

        // Remove the item from the cart and save the updated cart
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    /**
     * Updates the quantity of an item in the cart.
     * Recalculates the total price of the cart based on the updated quantity.
     *
     * @param cartId    the ID of the cart
     * @param productId the ID of the product to be updated
     * @param quantity  the new quantity of the product
     */
    @Transactional
    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        // Fetch the cart and the item to be updated
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    // Update the quantity and recalculate the price
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });

        // Recalculate the total amount of the cart
        BigDecimal totalAmount = cart.getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);

        // Save the updated cart
        cartRepository.save(cart);
    }

    /**
     * Retrieves a CartItem by its cart ID and product ID.
     * Throws a ResourceNotFoundException if the item is not found.
     *
     * @param cartId    the ID of the cart
     * @param productId the ID of the product
     * @return the CartItem associated with the cart and product
     */
    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found for productId: " + productId));
    }
}
