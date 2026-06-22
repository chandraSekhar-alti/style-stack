package com.example.stylestackapp.product.repository;

import com.example.stylestackapp.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, UUID> {
    List<Product> findByIsActiveIsTrue();

    Page<Product> findByIsActiveIsTrue(Pageable pageable);

    List<Product> findByCategoryId(UUID categoryId);
}
