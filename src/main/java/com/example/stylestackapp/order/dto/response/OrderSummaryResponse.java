package com.example.stylestackapp.order.dto.response;

import com.example.stylestackapp.common.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderSummaryResponse {

  private UUID orderId;

  private String orderNumber;

  private OrderStatus orderStatus;

  private BigDecimal totalAmount;

  private LocalDateTime placedAt;
}
