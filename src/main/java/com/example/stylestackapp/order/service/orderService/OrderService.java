package com.example.stylestackapp.order.service.orderService;

import com.example.stylestackapp.order.dto.response.CheckoutResponse;
import com.example.stylestackapp.order.dto.response.OrderDetailsResponse;
import com.example.stylestackapp.order.dto.response.OrderSummaryResponse;
import com.example.stylestackapp.security.service.CustomUserPrincipal;

import java.util.List;
import java.util.UUID;

public interface OrderService {

  CheckoutResponse checkout(CustomUserPrincipal principal);

  List<OrderSummaryResponse> getOrders(CustomUserPrincipal principal);

  OrderDetailsResponse getOrder(UUID orderId, CustomUserPrincipal principal);
}
