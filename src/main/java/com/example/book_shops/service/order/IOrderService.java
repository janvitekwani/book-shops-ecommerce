package com.example.book_shops.service.order;

import com.example.book_shops.DTO.OrderDto;
import com.example.book_shops.model.Order;

import java.util.List;

public interface IOrderService {
    // Method to place an order for a user
    Order placeOrder(Long userId);

    // Method to retrieve an order by ID
    OrderDto getOrder(Long orderId);

    // Method to retrieve all orders for a user
    List<OrderDto> getUserOrders(Long userId);
}
