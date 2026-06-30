package com.example.stylestackapp.product.service.ProductService;

import com.example.stylestackapp.product.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

  List<ProductResponse> getAllProducts();

  ProductResponse getProductById(UUID productId);
}
