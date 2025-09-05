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

    @GetMapping ("{orderId}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrderById(orderId))
                .message("Get order successfully")
                .build();
    }

    //VIP
    @GetMapping("/search")
    public ApiResponse<List<OrderResponse>> getOrders(
            @RequestParam(name= "page", defaultValue = "0") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @RequestParam(name = "sort", defaultValue = "orderId") String sort,
            @RequestParam(name = "order", defaultValue = "asc") String order,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "userId", required = false) BigInteger userId,
            @RequestParam(name = "status", required = false) String status
            ) {
        Sort.Direction direction = order.equalsIgnoreCase(("asc")) ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));
        return ApiResponse.<List<OrderResponse>>builder()
                .message("Get all orders successfully")
                .result(orderService.getOrders(pageable, userId, status, keyword))
                .build();
    }


    @PutMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(@PathVariable Long orderId,
                                                  @Valid @RequestBody OrderUpdateRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(orderId, request))
                .message("Update successfully")
                .build();
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<String> deleteOrder(@Valid @PathVariable Long orderId) {
        //TODO: delete softly
        orderService.deleteOrder(orderId);
        return ApiResponse.<String>builder()
                .message("delete successfully")
                .build();
    }
}
