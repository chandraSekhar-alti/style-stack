package com.example.stylestackapp.cart.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CartItemResponse {

    private UUID cartItemId;

    private UUID productId;

    private String productName;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal subTotal;

}
