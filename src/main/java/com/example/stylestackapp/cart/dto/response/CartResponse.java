package com.example.stylestackapp.cart.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class CartResponse {

    private List<CartItemResponse> items;

    private BigDecimal totalAmount;

}
