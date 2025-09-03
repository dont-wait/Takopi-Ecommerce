package com.dontwait.shopapp.mapper;

import com.dontwait.shopapp.dto.request.product.ProductCreationRequest;
import com.dontwait.shopapp.dto.request.product.ProductUpdateRequest;
import com.dontwait.shopapp.dto.response.ProductResponse;
import com.dontwait.shopapp.entity.Category;
import com.dontwait.shopapp.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "isActive", constant = "1")
    Product toProduct(ProductCreationRequest request, Category category);

    @Mapping(target = "productCategory", source = "category.categoryName")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "category")
    void updateProduct(ProductUpdateRequest request, @MappingTarget Product product, Category category);
}
