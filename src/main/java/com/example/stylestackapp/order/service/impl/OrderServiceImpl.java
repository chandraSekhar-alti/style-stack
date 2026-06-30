package com.example.stylestackapp.order.service.impl;

import com.example.stylestackapp.cart.entity.Cart;
import com.example.stylestackapp.cart.entity.CartItem;
import com.example.stylestackapp.cart.repository.CartItemRepo;
import com.example.stylestackapp.cart.repository.CartRepo;
import com.example.stylestackapp.common.enums.CartStatus;
import com.example.stylestackapp.common.enums.OrderStatus;
import com.example.stylestackapp.common.exceptions.BusinessException;
import com.example.stylestackapp.common.exceptions.ResourceNotFoundException;
import com.example.stylestackapp.common.exceptions.UnauthorizedException;
import com.example.stylestackapp.order.dto.response.CheckoutResponse;
import com.example.stylestackapp.order.dto.response.OrderDetailsResponse;
import com.example.stylestackapp.order.dto.response.OrderItemResponse;
import com.example.stylestackapp.order.dto.response.OrderSummaryResponse;
import com.example.stylestackapp.order.entity.Order;
import com.example.stylestackapp.order.entity.OrderItem;
import com.example.stylestackapp.order.repository.OrderItemRepo;
import com.example.stylestackapp.order.repository.OrderRepo;
import com.example.stylestackapp.order.service.orderService.OrderService;
import com.example.stylestackapp.product.entity.Product;
import com.example.stylestackapp.security.service.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepo orderRepo;
  private final OrderItemRepo orderItemRepo;
  private final CartRepo cartRepo;
  private final CartItemRepo cartItemRepo;

  @Override
  @Transactional
  public CheckoutResponse checkout(CustomUserPrincipal principal) {
    Cart cart =
        cartRepo
            .findByUserIdAndStatus(principal.getUserId(), CartStatus.ACTIVE)
            .orElseThrow(() -> new BusinessException("Cart not found"));

    // Find Active Cart
    List<CartItem> cartItems = cartItemRepo.findByCartId(cart.getId());

    // Validate Empty Cart
    if (cartItems.isEmpty()) {
      throw new BusinessException("Cart is empty");
    }

    // Validate Stock
    for (CartItem cartItem : cartItems) {
      Product product = cartItem.getProduct();

      if (cartItem.getQuantity() > product.getStockQuantity()) {
        throw new BusinessException("Insufficient stock for product");
      }
    }

    // Calculate Total
    BigDecimal totalAmount =
        cartItems.stream()
            .map(
                item ->
                    item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    // Generate Order Number
    String orderNumber = "ORD-" + System.currentTimeMillis();

    // Create Order
    Order order =
        Order.builder()
            .user(principal.getUser())
            .orderNumber(orderNumber)
            .status(OrderStatus.PENDING_PAYMENT)
            .totalAmount(totalAmount)
            .placedAt(LocalDateTime.now())
            .build();

    Order savedOrder = orderRepo.save(order);

    // Create Order Items
    List<OrderItem> orderItems = new ArrayList<>();

    for (CartItem cartItem : cartItems) {
      BigDecimal price = cartItem.getProduct().getPrice();

      BigDecimal subTotal = price.multiply(BigDecimal.valueOf(cartItem.getQuantity()));

      // Create Item
      OrderItem orderItem =
          OrderItem.builder()
              .order(savedOrder)
              .product(cartItem.getProduct())
              .quantity(cartItem.getQuantity())
              .price(price)
              .subTotal(subTotal)
              .build();

      orderItems.add(orderItem);
      orderItemRepo.saveAll(orderItems);
    }

    return CheckoutResponse.builder()
        .orderId(savedOrder.getId())
        .orderNumber(savedOrder.getOrderNumber())
        .totalAmount(savedOrder.getTotalAmount())
        .orderStatus(savedOrder.getStatus())
        .build();
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderSummaryResponse> getOrders(CustomUserPrincipal principal) {
    List<Order> orders = orderRepo.findByUserId(principal.getUserId());

    return orders.stream()
        .map(
            order ->
                OrderSummaryResponse.builder()
                    .orderId(order.getId())
                    .orderNumber(order.getOrderNumber())
                    .orderStatus(order.getStatus())
                    .totalAmount(order.getTotalAmount())
                    .placedAt(order.getPlacedAt())
                    .build())
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public OrderDetailsResponse getOrder(UUID orderId, CustomUserPrincipal principal) {

    // Find Order
    Order order =
        orderRepo
            .findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

    // Ownership Validation
    if (!order.getUser().getId().equals(principal.getUserId())) {
      throw new UnauthorizedException("You are not authorized to access this order");
    }

    // Load Order Items
    List<OrderItem> orderItems = orderItemRepo.findByOrderId(orderId);

    // Map Items
    List<OrderItemResponse> itemResponses =
        orderItems.stream()
            .map(
                item ->
                    OrderItemResponse.builder()
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .subTotal(item.getSubTotal())
                        .build())
            .toList();

    return OrderDetailsResponse.builder()
        .orderId(order.getId())
        .orderNumber((order.getOrderNumber()))
        .status(order.getStatus())
        .totalAmount(order.getTotalAmount())
        .placedAt(order.getPlacedAt())
        .items(itemResponses)
        .build();
  }
}
