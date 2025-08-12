package com.dontwait.shopapp.service.impl;

import com.dontwait.shopapp.dto.request.order.OrderCreationRequest;
import com.dontwait.shopapp.dto.request.order.OrderUpdateRequest;
import com.dontwait.shopapp.dto.response.OrderResponse;
import com.dontwait.shopapp.entity.Order;
import com.dontwait.shopapp.entity.OrderDetail;
import com.dontwait.shopapp.entity.Product;
import com.dontwait.shopapp.entity.User;
import com.dontwait.shopapp.enums.ErrorCode;
import com.dontwait.shopapp.exception.AppException;
import com.dontwait.shopapp.mapper.OrderMapper;
import com.dontwait.shopapp.repository.OrderDetailRepository;
import com.dontwait.shopapp.repository.OrderRepository;
import com.dontwait.shopapp.repository.ProductRepository;
import com.dontwait.shopapp.repository.UserRepository;
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
    ProductRepository productRepository;
    OrderMapper orderMapper;
    UserRepository userRepository;

    @Override
    public OrderResponse createOrder(OrderCreationRequest request) {

        User userExisting = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_FOUND));

        //Check product existed by id before save to Order with OrderDetails
        List<OrderDetail> details = request.getOrderDetails().stream()
                .map(detailReq -> {
                    Product existingProduct = productRepository.findById(detailReq.getProductId())
                            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));

                    return orderMapper.toOrderDetail(detailReq, existingProduct);
                })
                .toList();

        Order newOrder = orderMapper.toOrder(request, userExisting);
        newOrder.setOrderDetails(details);
        details.forEach(detail -> detail.setOrder(newOrder));

        return orderMapper.toOrderResponse(orderRepository.save(newOrder));
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        return null;
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserUserId(userId).stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public void deleteOrder(Long orderId) {

    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        return null;
    }
}
