package com.dontwait.shopapp.controller;

import com.dontwait.shopapp.dto.request.order.OrderCreationRequest;
import com.dontwait.shopapp.dto.request.order.OrderUpdateRequest;
import com.dontwait.shopapp.dto.response.ApiResponse;
import com.dontwait.shopapp.dto.response.OrderResponse;
import com.dontwait.shopapp.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreationRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .message("Create order successfully")
                .build();
    }

    //VIP
    @GetMapping
    public ApiResponse<List<OrderResponse>> getOrders(
            @RequestParam(name= "page", defaultValue = "0") Integer page,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(name = "sort", defaultValue = "orderId") String sort,
            @RequestParam(name = "order", defaultValue = "asc") String order,
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "userId") BigInteger userId,
            @RequestParam(name = "status") String status
            ) {
        Sort.Direction direction = order.equalsIgnoreCase(("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        return ApiResponse.<List<OrderResponse>>builder()
                .message("Get all orders successfully")
                //.result()
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
