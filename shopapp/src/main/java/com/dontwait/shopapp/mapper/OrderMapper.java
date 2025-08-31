package com.dontwait.shopapp.mapper;

import com.dontwait.shopapp.dto.request.order.OrderCreationRequest;
import com.dontwait.shopapp.dto.request.order.OrderUpdateRequest;
import com.dontwait.shopapp.dto.request.order_detail.OrderDetailCreationRequest;
import com.dontwait.shopapp.dto.request.order_detail.OrderDetailUpdateRequest;
import com.dontwait.shopapp.dto.response.OrderDetailResponse;
import com.dontwait.shopapp.dto.response.OrderResponse;
import com.dontwait.shopapp.entity.Order;
import com.dontwait.shopapp.entity.OrderDetail;
import com.dontwait.shopapp.entity.Product;
import com.dontwait.shopapp.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "user")
    Order toOrder(OrderCreationRequest request, User user);

    OrderDetail toOrderDetail(OrderDetailCreationRequest request, Product product);

    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "productName", source = "product.productName")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    //Update Order
    @BeanMapping(ignoreByDefault = true,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateOrder(OrderUpdateRequest request, @MappingTarget Order order);

    @BeanMapping(ignoreByDefault = true,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateOrderDetail(OrderDetailUpdateRequest request, @MappingTarget OrderDetail orderDetail);

    @AfterMapping
    default void linkOrderDetails(@MappingTarget Order order) {
        if(order.getOrderDetails() != null)
            order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));
    }

}
