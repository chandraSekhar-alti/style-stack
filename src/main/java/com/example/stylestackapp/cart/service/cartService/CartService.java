package com.example.stylestackapp.cart.service.cartService;

import com.example.stylestackapp.cart.dto.request.AddToCartRequest;
import com.example.stylestackapp.cart.dto.response.CartResponse;
import com.example.stylestackapp.security.service.CustomUserPrincipal;

import java.util.UUID;

public interface CartService {

  void addToCart(AddToCartRequest cartRequest, CustomUserPrincipal principal);

  CartResponse getCart(CustomUserPrincipal principal);

  void removeCartItem(UUID cartItemId, CustomUserPrincipal principal);

  void updateQuantity(UUID cartItemId, Integer quantity, CustomUserPrincipal principal);
}
