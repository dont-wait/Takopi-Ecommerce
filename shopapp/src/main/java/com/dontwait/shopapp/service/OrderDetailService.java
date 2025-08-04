package com.dontwait.shopapp.service;

import com.dontwait.shopapp.dto.request.order_detail.OrderDetailUpdateByAdminRequest;
import com.dontwait.shopapp.dto.request.order_detail.OrderDetailUpdateByUserRequest;
import com.dontwait.shopapp.dto.response.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {
    OrderDetailResponse createOrderDetail(Long orderId);
    List<OrderDetailResponse> getOrderDetailsByOrderId(Long orderId);
    void deleteOrderDetail(Long orderDetailId);
    OrderDetailResponse updateOrderDetailByAdmin(Long orderDetailId, OrderDetailUpdateByAdminRequest request);
    OrderDetailResponse updateOrderDetailByUser(Long orderDetailId, OrderDetailUpdateByUserRequest request);
}
