package com.example.stylestackapp.cart.controller;

import com.example.stylestackapp.cart.dto.request.AddToCartRequest;
import com.example.stylestackapp.cart.dto.response.CartResponse;
import com.example.stylestackapp.cart.service.cartService.CartService;
import com.example.stylestackapp.common.response.ApiResponse.ApiResponse;
import com.example.stylestackapp.security.service.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @PostMapping("/items")
  public ResponseEntity<ApiResponse<Void>> addToCart(
      @Valid @RequestBody AddToCartRequest request,
      @AuthenticationPrincipal CustomUserPrincipal principal) {

    cartService.addToCart(request, principal);

    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .success(true)
            .message("Product added to cart successfully")
            .timeStamp(LocalDateTime.now())
            .build());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<CartResponse>> getCart(
      @AuthenticationPrincipal CustomUserPrincipal principal) {

    CartResponse response = cartService.getCart(principal);

    return ResponseEntity.ok(
        ApiResponse.<CartResponse>builder()
            .success(true)
            .message("Cart fetched successfully")
            .data(response)
            .timeStamp(LocalDateTime.now())
            .build());
  }

  @PutMapping("/items/{cartItemId}")
  public ResponseEntity<ApiResponse<Void>> updateQuantity(
      @PathVariable UUID cartItemId,
      @RequestParam Integer quantity,
      @AuthenticationPrincipal CustomUserPrincipal principal) {

    cartService.updateQuantity(cartItemId, quantity, principal);

    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .success(true)
            .message("Cart item updated successfully")
            .timeStamp(LocalDateTime.now())
            .build());
  }

  @DeleteMapping("/items/{cartItemId}")
  public ResponseEntity<ApiResponse<Void>> removeCartItem(
      @PathVariable UUID cartItemId, @AuthenticationPrincipal CustomUserPrincipal principal) {

    cartService.removeCartItem(cartItemId, principal);

    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .success(true)
            .message("Cart item removed successfully")
            .timeStamp(LocalDateTime.now())
            .build());
  }
}
