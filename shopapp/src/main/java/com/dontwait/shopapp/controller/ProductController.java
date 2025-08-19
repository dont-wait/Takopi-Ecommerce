package com.dontwait.shopapp.controller;

import com.dontwait.shopapp.dto.request.product.ProductCreationRequest;
import com.dontwait.shopapp.dto.request.product.ProductUpdateRequest;
import com.dontwait.shopapp.dto.response.ApiResponse;
import com.dontwait.shopapp.dto.response.ProductImageResponse;
import com.dontwait.shopapp.dto.response.ProductResponse;
import com.dontwait.shopapp.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    @GetMapping("{productId}")
    public ApiResponse<ProductResponse> findProductById(@PathVariable Long productId) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.findProductById(productId))
                .message("Get product successfully")
                .build();
    }
    @GetMapping
    public ApiResponse<List<ProductResponse>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                                      @RequestParam(name = "keyword", required = false) String keyword,
                                                      @RequestParam(name = "categoryId", required = false) Long categoryId,
                                                      @RequestParam(name = "sort", defaultValue = "productId") String sort,
                                                      @RequestParam(name = "order", defaultValue = "asc") String order) {
        Sort.Direction direction = order.equalsIgnoreCase(("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));

        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.findAllProducts(pageable, keyword, categoryId))
                .message("Get all products successfully")
                .build();
    }

    @PostMapping()
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreationRequest request) throws IOException {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .message("Create product successfully")
                .build();
    }

    @PostMapping(value = "/uploads/{productId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<ProductImageResponse>> uploadImages(@PathVariable Long productId,
                                                          @ModelAttribute("files")List<MultipartFile> files) throws IOException {
        List<ProductImageResponse> productImageResponses = productService.uploadImages(productId, files);
        return ApiResponse.<List<ProductImageResponse>>builder()
                .result(productImageResponses)
                .message("Upload product images successfully")
                .build();
    }


    @PutMapping("{productId}")
    public ApiResponse<ProductResponse> updateProduct( @PathVariable Long productId, @Valid @RequestBody ProductUpdateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId, request))
                .build();
    }

    @DeleteMapping("{productId}")
    public ApiResponse<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.<String>builder()
                .message("Delete product successfully")
                .build();
    }
}
