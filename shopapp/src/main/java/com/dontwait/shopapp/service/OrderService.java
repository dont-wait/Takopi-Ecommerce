package com.dontwait.shopapp.service;

import com.dontwait.shopapp.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder();
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getOrdersByUserId(Long userId);
    void deleteOrder(Long orderId);
    OrderResponse updateOrder(Long orderId);
}
