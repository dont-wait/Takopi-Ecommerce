package com.dontwait.shopapp.dto.response;

import com.dontwait.shopapp.entity.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long productId;
    String productName;
    BigDecimal productPrice;
    String productThumbnail;
    String productDescription;
    String productCategory;
    Integer isActive;
}
