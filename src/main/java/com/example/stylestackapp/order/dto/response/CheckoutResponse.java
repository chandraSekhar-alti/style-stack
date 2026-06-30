package com.example.stylestackapp.order.dto.response;

import com.example.stylestackapp.common.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {

  private UUID orderId;

  private String orderNumber;

  private BigDecimal totalAmount;

  private OrderStatus orderStatus;
}
