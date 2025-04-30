package com.example.book_shops.service.order;


import com.example.book_shops.DTO.OrderDto;
import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.enums.OrderStatus;
import com.example.book_shops.model.Cart;
import com.example.book_shops.model.Order;
import com.example.book_shops.model.OrderItem;
import com.example.book_shops.model.Product;
import com.example.book_shops.repository.OrderRepository;
import com.example.book_shops.repository.ProductRepository;
import com.example.book_shops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    // Injecting required dependencies
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        // Fetch the user's cart
        Cart cart = cartService.getCartByUserId(userId);

        // Create a new order based on the cart
        Order order = createOrder(cart);

        // Create order items from the cart's items
        List<OrderItem> orderItemList = createOrderItems(order, cart);

        // Set order items and calculate total amount for the order
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        // Save the order to the repository and clear the cart
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return savedOrder;  // Return the saved order
    }

    private Order createOrder(Cart cart) {
        // Create and populate a new Order object
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);  // Set default status to pending
        order.setOrderDate(LocalDate.now());  // Set the current date as the order date
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        // Create a list of order items by mapping cart items to order items
        return cart.getItems().stream().map(cartItem -> {
            // Update product inventory after adding it to the order
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);

            // Return a new OrderItem
            return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        // Calculate the total amount of the order by summing up the prices of order items
        return orderItemList.stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        // Fetch order by ID and convert it to OrderDto
        return orderRepository.findById(orderId)
                .map(this::convertToDto)  // Convert to DTO
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));  // Handle not found scenario
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        // Fetch all orders for the user and convert them to OrderDto list
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    // Helper method to convert Order to OrderDto
    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}