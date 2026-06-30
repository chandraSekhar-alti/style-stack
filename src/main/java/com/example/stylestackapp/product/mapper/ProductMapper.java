package com.example.stylestackapp.product.mapper;

import com.example.stylestackapp.product.dto.response.ProductResponse;
import com.example.stylestackapp.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public ProductResponse toProductResponse(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .stockQuantity(product.getStockQuantity())
        .imageUrl(product.getImageUrl())
        .categoryName(product.getCategory().getName())
        .build();
  }
}
