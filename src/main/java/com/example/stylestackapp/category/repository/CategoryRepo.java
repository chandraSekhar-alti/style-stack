package com.example.stylestackapp.category.repository;

import com.example.stylestackapp.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {

  Optional<Category> findBySlug(String slug);

  boolean existsBySlug(String slug);

  boolean existsByNameIgnoreCase(String name);

  List<Category> findByActiveTrue();
}
