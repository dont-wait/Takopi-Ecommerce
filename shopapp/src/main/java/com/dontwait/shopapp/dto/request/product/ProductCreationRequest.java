package com.dontwait.shopapp.dto.request.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {
    @Size(min = 3, message = "PRODUCT_NAME_MUST_BE_GREATER_THAN_3")
    String productName;
    @DecimalMin(value = "0.01", message = "PRODUCT_PRICE_MUST_BE_GREATER_THAN_0")
    BigDecimal productPrice;
    String productThumbnail;
    String productDescription;
    @NotNull(message = "CATEGORY_ID_CANT_NULL")
    Long categoryId;
}
