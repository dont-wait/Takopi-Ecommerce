package com.dontwait.shopapp.dto.request.order_detail;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailUpdateRequest {

    Long orderDetailId;

    @Min(value = 1, message = "PRODUCT_ID_MUST_BE_GREATER_THAN_1")
    Long productId;;

    @Min(value = 0, message = "PRODUCT_PRICE_MUST_BE_GREATER_THAN_0")
    BigDecimal price;
    @Min(value = 1, message = "PRODUCT_NUMBER_MUST_BE_GREATER_THAN_1")
    Integer numberOfProducts;
    @Min(value = 0, message = "PRODUCT_TOTAL_MONEY_MUST_BE_GREATER_THAN_0")
    BigDecimal totalMoney;
    String color;
}
