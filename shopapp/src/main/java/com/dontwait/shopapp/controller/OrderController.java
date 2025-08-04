package com.dontwait.shopapp.controller;

import com.dontwait.shopapp.dto.request.order.OrderCreationRequest;
import com.dontwait.shopapp.dto.request.order.OrderUpdateRequest;
import com.dontwait.shopapp.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public ApiResponse<String> createOrder(@Valid @RequestBody OrderCreationRequest request) {
        return ApiResponse.<String>builder()
                .message("Create order successfully")
                .build();
    }

    //TODO: Pageable
    @GetMapping("/{userId}")
    public ApiResponse<String> getOrders(@Valid @PathVariable Long userId) {
        return ApiResponse.<String>builder()
                .message("Get all orders successfully")
                .build();
    }

    @PutMapping("/{orderId}")
    public ApiResponse<String> updateOrder(@Valid @PathVariable Long orderId,
                                           @RequestBody OrderUpdateRequest request) {
        return ApiResponse.<String>builder()
                .message("Update successfully")
                .build();
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<String> deleteOrder(@Valid @PathVariable Long orderId) {
        //TODO: delete softly
        return ApiResponse.<String>builder()
                .message("delete successfully")
                .build();
    }

}
