package com.dontwait.shopapp.dto.response;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    Long orderId;

    String orderFullName;

    String orderEmail;

    String orderPhoneNumber;

    String address;

    String note;

    BigDecimal totalMoney;

    String shippingMethod;

    String shippingAddress;

    String paymentMethod;

    LocalDateTime createdAt;

    List<OrderDetailResponse> orderDetails;
}
