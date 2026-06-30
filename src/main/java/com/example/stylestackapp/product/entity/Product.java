package com.example.stylestackapp.product.entity;

import com.example.stylestackapp.auth.entity.AuditableEntity;
import com.example.stylestackapp.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AuditableEntity {

  @Column(nullable = false, length = 200)
  private String name;

  @Column(length = 2000)
  private String description;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal price;

  @Column(name = "stock_quantity", nullable = false)
  private Integer stockQuantity;

  @Column(name = "image_url")
  private String imageUrl;

  @Builder.Default
  @Column(name = "active", nullable = false)
  private boolean isActive = true;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;
}
