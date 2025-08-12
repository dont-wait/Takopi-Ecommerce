package com.dontwait.shopapp.service.impl;

import com.dontwait.shopapp.dto.request.order.OrderCreationRequest;
import com.dontwait.shopapp.dto.request.order.OrderUpdateRequest;
import com.dontwait.shopapp.dto.response.OrderResponse;
import com.dontwait.shopapp.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderResponse createOrder(OrderCreationRequest request) {
        return null;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        return null;
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return List.of();
    }

    @Override
    public void deleteOrder(Long orderId) {

    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        return null;
    }
}
