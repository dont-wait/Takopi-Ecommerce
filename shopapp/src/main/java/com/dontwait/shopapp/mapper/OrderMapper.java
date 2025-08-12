package com.dontwait.shopapp.mapper;

import com.dontwait.shopapp.dto.request.order.OrderCreationRequest;
import com.dontwait.shopapp.dto.request.order_detail.OrderDetailCreationRequest;
import com.dontwait.shopapp.dto.response.OrderResponse;
import com.dontwait.shopapp.entity.Order;
import com.dontwait.shopapp.entity.OrderDetail;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderCreationRequest request);
    OrderDetail toOrderDetail(OrderDetailCreationRequest request);

    OrderResponse toOrderResponse(Order order);

    @AfterMapping
    default void linkOrderDetails(@MappingTarget Order order) {
        if(order.getOrderDetails() != null)
            order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));
    }

}
