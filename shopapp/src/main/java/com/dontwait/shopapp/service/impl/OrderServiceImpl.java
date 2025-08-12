package com.dontwait.shopapp.service.impl;

import com.dontwait.shopapp.dto.request.order.OrderCreationRequest;
import com.dontwait.shopapp.dto.request.order.OrderUpdateRequest;
import com.dontwait.shopapp.dto.response.OrderResponse;
import com.dontwait.shopapp.entity.Order;
import com.dontwait.shopapp.mapper.OrderMapper;
import com.dontwait.shopapp.repository.OrderDetailRepository;
import com.dontwait.shopapp.repository.OrderRepository;
import com.dontwait.shopapp.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;
    OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderCreationRequest request) {
        Order newOrder = orderMapper.toOrder(request);

        if(newOrder.getOrderDetails() != null)
            newOrder.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(newOrder));

        return orderMapper.toOrderResponse(orderRepository.save(newOrder));
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
