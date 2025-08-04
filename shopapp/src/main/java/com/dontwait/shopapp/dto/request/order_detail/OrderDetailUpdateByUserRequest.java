package com.dontwait.shopapp.dto.request.order_detail;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailUpdateByUserRequest {
    @Min(value = 1, message = "PRODUCT_NUMBER_MUST_BE_GREATER_THAN_1")
    Integer numberOfProducts;
    String color;
}
