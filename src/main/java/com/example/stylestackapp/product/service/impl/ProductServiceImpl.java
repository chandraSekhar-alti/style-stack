package com.example.stylestackapp.product.service.impl;

import com.example.stylestackapp.product.dto.response.ProductResponse;
import com.example.stylestackapp.product.entity.Product;
import com.example.stylestackapp.product.mapper.ProductMapper;
import com.example.stylestackapp.product.repository.ProductRepo;
import com.example.stylestackapp.product.service.ProductService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productsRepo;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productsRepo.
                findByIsActiveIsTrue()
                .stream()
                .map(productMapper :: toProductResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID productId) {
        Product product = productsRepo.
                findById(productId)
                .orElseThrow(()-> new ResourceAccessException("Product not found"));

        return productMapper.toProductResponse(product);
    }
}
