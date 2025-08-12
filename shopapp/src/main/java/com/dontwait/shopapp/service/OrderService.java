package com.dontwait.shopapp.service;

import com.dontwait.shopapp.dto.request.order.OrderCreationRequest;
import com.dontwait.shopapp.dto.request.order.OrderUpdateRequest;
import com.dontwait.shopapp.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderCreationRequest request);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getOrdersByUserId(Long userId);
    void deleteOrder(Long orderId);
    OrderResponse updateOrder(Long orderId, OrderUpdateRequest request);
}
