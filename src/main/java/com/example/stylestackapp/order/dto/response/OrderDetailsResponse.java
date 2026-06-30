package com.example.stylestackapp.order.dto.response;

import com.example.stylestackapp.common.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderDetailsResponse {

  private UUID orderId;

  private String orderNumber;

  private OrderStatus status;

  private BigDecimal totalAmount;

  private LocalDateTime placedAt;

  private List<OrderItemResponse> items;
}
