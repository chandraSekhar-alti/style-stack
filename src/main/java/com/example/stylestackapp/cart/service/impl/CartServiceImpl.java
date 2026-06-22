package com.example.stylestackapp.cart.service.impl;

import com.example.stylestackapp.cart.dto.request.AddToCartRequest;
import com.example.stylestackapp.cart.dto.response.CartItemResponse;
import com.example.stylestackapp.cart.dto.response.CartResponse;
import com.example.stylestackapp.cart.entity.Cart;
import com.example.stylestackapp.cart.entity.CartItem;
import com.example.stylestackapp.cart.repository.CartItemRepo;
import com.example.stylestackapp.cart.repository.CartRepo;
import com.example.stylestackapp.cart.service.cartService.CartService;
import com.example.stylestackapp.common.enums.CartStatus;
import com.example.stylestackapp.common.exceptions.BusinessException;
import com.example.stylestackapp.common.exceptions.ResourceNotFoundException;
import com.example.stylestackapp.product.entity.Product;
import com.example.stylestackapp.product.repository.ProductRepo;
import com.example.stylestackapp.security.service.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;


    @Override
    @Transactional
    public void addToCart(AddToCartRequest cartRequest, CustomUserPrincipal principal) {
        Product product = productRepo.
                findById(cartRequest.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id : " + cartRequest.getProductId())
                );

        if (!product.isActive()) {
            throw new BusinessException(
                    "Product is currently unavailable");
        }

        Cart cart = cartRepo
                .findByUserIdAndStatus(
                        principal.getUserId(),
                        CartStatus.ACTIVE)
                .orElseGet(() -> {

                    Cart newCart = Cart.builder()
                            .user(principal.getUser())
                            .status(CartStatus.ACTIVE)
                            .build();

                    return cartRepo.save(newCart);
                });

        Optional<CartItem> existingCartItem =
                cartItemRepo.findByCartIdAndProductId(
                        cart.getId(),
                        product.getId());

        if (existingCartItem.isPresent()) {

            CartItem cartItem = existingCartItem.get();

            int newQuantity =
                    cartItem.getQuantity()
                            + cartRequest.getQuantity();

            if (newQuantity > product.getStockQuantity()) {
                throw new BusinessException(
                        "Insufficient stock available");
            }

            cartItem.setQuantity(newQuantity);
            cartItemRepo.save(cartItem);

        }else {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(cartRequest.getQuantity())
                    .build();

            cartItemRepo.save(cartItem);
        }
    }

    @Override
    public CartResponse getCart(CustomUserPrincipal principal) {
        Optional<Cart> cartOptional =
                cartRepo.findByUserIdAndStatus(
                        principal.getUserId(),
                        CartStatus.ACTIVE);


        if (cartOptional.isEmpty()) {

            return CartResponse.builder()
                    .items(List.of())
                    .totalAmount(BigDecimal.ZERO)
                    .build();
        }

        Cart cart = cartOptional.get();

        List<CartItem> cartItems = cartItemRepo.findByCartId(cart.getId());

        List<CartItemResponse> itemResponses = cartItems.stream()
                .map(cartItem -> {
                    BigDecimal subTotal =
                            cartItem.getProduct()
                                    .getPrice()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    cartItem.getQuantity()));
                    return CartItemResponse.builder()
                            .cartItemId(cartItem.getId())
                            .productId(cartItem.getProduct().getId())
                            .productName(cartItem.getProduct().getName())
                            .price(cartItem.getProduct().getPrice())
                            .quantity(cartItem.getQuantity())
                            .subTotal(subTotal)
                            .build();
                })
                .toList();

        BigDecimal totalAmount =
                itemResponses.stream()
                        .map(CartItemResponse::getSubTotal)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add);

        return CartResponse.builder()
                .items(itemResponses)
                .totalAmount(totalAmount)
                .build();

    }

    @Override
    @Transactional
    public void removeCartItem(
            UUID cartItemId,
            CustomUserPrincipal principal) {

        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found"));

        if (!cartItem.getCart().getUser().getId().equals(principal.getUserId())) {
            throw new BusinessException(
                    "You cannot remove another user's cart item");
        }

        cartItemRepo.delete(cartItem);
        log.info("Cart item removed. userId={}, cartItemId={}", principal.getUserId(), cartItemId);
    }

    @Override
    @Transactional
    public void updateQuantity(UUID cartItemId, Integer quantity, CustomUserPrincipal principal) {

        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getCart().getUser().getId().equals(principal.getUserId())) {
            throw new BusinessException("You cannot update another user's cart item");
        }

        Product product = cartItem.getProduct();

        if (quantity > product.getStockQuantity()) {
            throw new BusinessException("Insufficient stock available");
        }

        cartItem.setQuantity(quantity);

        cartItemRepo.save(cartItem);
        log.info("Cart quantity updated. userId={}, cartItemId={}, quantity={}", principal.getUserId(), cartItemId, quantity);
    }
}
