package com.example.stylestackapp.order.controller;

import com.example.stylestackapp.common.response.ApiResponse.ApiResponse;
import com.example.stylestackapp.order.dto.response.CheckoutResponse;
import com.example.stylestackapp.order.dto.response.OrderDetailsResponse;
import com.example.stylestackapp.order.dto.response.OrderSummaryResponse;
import com.example.stylestackapp.order.service.orderService.OrderService;
import com.example.stylestackapp.security.service.CustomUserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/checkout")
  public ResponseEntity<ApiResponse<CheckoutResponse>> checkout(
      @AuthenticationPrincipal CustomUserPrincipal principal) {
    CheckoutResponse checkoutResponse = orderService.checkout(principal);
    return ResponseEntity.ok(
        ApiResponse.<CheckoutResponse>builder()
            .success(true)
            .data(checkoutResponse)
            .message("Checkout completed successfully")
            .build());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<OrderSummaryResponse>>> getOrders(
      @AuthenticationPrincipal CustomUserPrincipal principal) {

    List<OrderSummaryResponse> response = orderService.getOrders(principal);

    return ResponseEntity.ok(
        ApiResponse.<List<OrderSummaryResponse>>builder()
            .success(true)
            .message("Orders fetched successfully")
            .data(response)
            .timeStamp(LocalDateTime.now())
            .build());
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse<OrderDetailsResponse>> getOrder(
      @PathVariable UUID orderId, @AuthenticationPrincipal CustomUserPrincipal principal) {

    OrderDetailsResponse response = orderService.getOrder(orderId, principal);

    return ResponseEntity.ok(
        ApiResponse.<OrderDetailsResponse>builder()
            .success(true)
            .message("Order fetched successfully")
            .data(response)
            .timeStamp(LocalDateTime.now())
            .build());
  }
}
