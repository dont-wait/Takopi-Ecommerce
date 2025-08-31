package com.dontwait.shopapp.dto.request.order;

import com.dontwait.shopapp.dto.request.order_detail.OrderDetailUpdateRequest;
import com.dontwait.shopapp.entity.OrderDetail;
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
public class OrderUpdateRequest {

    String orderFullName;

    @Email(message = "EMAIL_INVALID")
    String orderEmail;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, message = "PHONE_NUMBER_MUST_BE_10_DIGIT")
    String orderPhoneNumber;

    String address;

    String note;

    @Min(value = 0, message = "Total money must be >= 0")
    BigDecimal totalMoney;

    String shippingMethod;

    String shippingAddress;

    String paymentMethod;

    List<OrderDetailUpdateRequest> orderDetails;
}
