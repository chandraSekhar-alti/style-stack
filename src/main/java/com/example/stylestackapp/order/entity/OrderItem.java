package com.example.stylestackapp.order.entity;

import com.example.stylestackapp.auth.entity.AuditableEntity;
import com.example.stylestackapp.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends AuditableEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal price;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal subTotal;
}
