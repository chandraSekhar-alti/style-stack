package com.example.stylestackapp.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddToCartRequest {

  @NotNull
  private UUID productId;

  @Min(1)
  private Integer quantity;
}
