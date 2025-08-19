package com.dontwait.shopapp.service;

import com.dontwait.shopapp.dto.request.order.OrderCreationRequest;
import com.dontwait.shopapp.dto.request.order.OrderUpdateRequest;
import com.dontwait.shopapp.dto.response.OrderResponse;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderCreationRequest request);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getOrders(Pageable pageable, BigInteger userId, String status, String keyword);
    void deleteOrder(Long orderId);
    OrderResponse updateOrder(Long orderId, OrderUpdateRequest request);
}
