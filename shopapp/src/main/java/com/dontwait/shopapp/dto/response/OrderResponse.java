package com.dontwait.shopapp.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    @Min(value = 1, message = "USER_ID_MUST_BE_GREATER_THAN_1")
    Long userId;

    String orderFullName;

    String orderEmail;

    String orderPhoneNumber;

    String address;

    String note;

    BigDecimal totalMoney;

    String shippingMethod;

    String shippingAddress;

    String paymentMethod;

    List<OrderDetailResponse> items;
}
