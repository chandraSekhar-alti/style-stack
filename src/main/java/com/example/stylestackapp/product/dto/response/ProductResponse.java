package com.example.stylestackapp.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductResponse {

    private UUID id;

    private String description;

    private String name;

    private BigDecimal price;

    private Integer stockQuantity;

    private String imageUrl;

    private String categoryName;
}
