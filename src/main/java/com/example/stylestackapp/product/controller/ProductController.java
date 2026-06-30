package com.example.stylestackapp.product.controller;

import com.example.stylestackapp.common.response.ApiResponse.ApiResponse;
import com.example.stylestackapp.product.dto.response.ProductResponse;
import com.example.stylestackapp.product.service.ProductService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
    List<ProductResponse> products = productService.getAllProducts();

    return ResponseEntity.ok(
        ApiResponse.<List<ProductResponse>>builder()
            .success(true)
            .message("Products fetched successfully")
            .data(products)
            .timeStamp(LocalDateTime.now())
            .build());
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable UUID productId) {

    ProductResponse product = productService.getProductById(productId);

    return ResponseEntity.ok(
        ApiResponse.<ProductResponse>builder()
            .success(true)
            .message("Product fetched successfully")
            .data(product)
            .timeStamp(LocalDateTime.now())
            .build());
  }
}
