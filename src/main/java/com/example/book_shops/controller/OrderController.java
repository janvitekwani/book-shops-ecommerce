package com.example.book_shops.controller;

import com.example.book_shops.DTO.OrderDto;
import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.model.Order;
import com.example.book_shops.response.ApiResponse;
import com.example.book_shops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;

    /**
     * Place a new order for a user.
     *
     * @param userId ID of the user placing the order.
     * @return ApiResponse indicating the result of the order placement.
     */
    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            // Attempt to place the order
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (Exception e) {
            // Handle any exceptions that occur during order placement
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error Occured!", e.getMessage()));
        }
    }

    /**
     * Retrieve details of a specific order by its ID.
     *
     * @param orderId ID of the order to retrieve.
     * @return ApiResponse containing the order details.
     */
    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            // Attempt to retrieve the order details
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            // Handle case where the order is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Oops!", e.getMessage()));
        }
    }

    /**
     * Retrieve all orders placed by a specific user.
     *
     * @param userId ID of the user whose orders to retrieve.
     * @return ApiResponse containing a list of the user's orders.
     */
    @GetMapping("/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            // Attempt to retrieve the user's orders
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", orders));
        } catch (ResourceNotFoundException e) {
            // Handle case where no orders are found for the user
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Oops!", e.getMessage()));
        }
    }
}