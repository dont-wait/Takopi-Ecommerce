package com.dontwait.shopapp.service.impl;

import com.dontwait.shopapp.dto.request.order.OrderCreationRequest;
import com.dontwait.shopapp.dto.request.order.OrderUpdateRequest;
import com.dontwait.shopapp.dto.response.OrderResponse;
import com.dontwait.shopapp.entity.*;
import com.dontwait.shopapp.enums.ErrorCode;
import com.dontwait.shopapp.exception.AppException;
import com.dontwait.shopapp.mapper.OrderMapper;
import com.dontwait.shopapp.repository.OrderDetailRepository;
import com.dontwait.shopapp.repository.OrderRepository;
import com.dontwait.shopapp.repository.ProductRepository;
import com.dontwait.shopapp.repository.UserRepository;
import com.dontwait.shopapp.service.OrderService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    ProductRepository productRepository;
    OrderMapper orderMapper;
    UserRepository userRepository;
    OrderDetailRepository orderDetailRepository;

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
        newOrder.setOrderStatus(OrderStatus.PENDING);

        details.forEach(detail -> detail.setOrder(newOrder));

        return orderMapper.toOrderResponse(orderRepository.save(newOrder));
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order existingOrder = orderRepository.findByOrderId(orderId)
                .filter(order -> order.getActive() == 1)
                .orElseThrow(() ->
                        new AppException(ErrorCode.ORDER_ID_NOT_FOUND));
        return orderMapper.toOrderResponse(existingOrder);
    }

    @Override
    public List<OrderResponse> getOrders(Pageable pageable,
                                         BigInteger userId,
                                         String status,
                                         String keyword) {
        Specification<Order> spec = Specification.where(null);
        if(userId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("user").get("userId"), userId));
        }

        if(status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                List<Predicate> keyWordPredicates = new ArrayList<>();
                // filter trên trường của Order
                keyWordPredicates.add(cb.like(root.get("orderFullName"), "%" + keyword + "%"));
                keyWordPredicates.add(cb.like(root.get("trackingNumber"), "%" + keyword + "%"));

                // join đến OrderDetail và Product để filter productName
                Join<Order, OrderDetail> detailJoin = root.join("orderDetails", JoinType.LEFT);
                Join<OrderDetail, Product> productJoin = detailJoin.join("product", JoinType.LEFT);
                keyWordPredicates.add(cb.like(productJoin.get("productName"), "%" + keyword + "%"));

                // combine tất cả điều kiện bằng OR
                return cb.or(keyWordPredicates.toArray(new Predicate[0]));
            });
        }


        return orderRepository.findAll(spec, pageable)
                .stream()
                .filter(order -> order.getActive() == 1)
                .map(orderMapper::toOrderResponse)
                .toList();

    }

    @Override
    public void deleteOrder(Long orderId) {
        Order existingORder = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ID_NOT_FOUND));
        existingORder.setActive(0);
        orderRepository.save(existingORder);
    }

    @Override
    //TODO: Implement check role each field for update
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {

        //Get order in db to update
        Order orderExisting = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ID_NOT_FOUND));

        orderMapper.updateOrder(request, orderExisting);
        orderRepository.save(orderExisting);
        return orderMapper.toOrderResponse(orderExisting);
    }
}
