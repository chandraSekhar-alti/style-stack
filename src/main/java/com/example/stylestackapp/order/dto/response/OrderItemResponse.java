package com.example.stylestackapp.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderItemResponse {

    private String productName;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal subTotal;

}
