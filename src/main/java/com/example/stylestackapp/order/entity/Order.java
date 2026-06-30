package com.example.stylestackapp.order.entity;

import com.example.stylestackapp.auth.entity.AuditableEntity;
import com.example.stylestackapp.auth.entity.User;
import com.example.stylestackapp.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends AuditableEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "order_number", nullable = false, unique = true)
  private String orderNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus status;

  @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
  private BigDecimal totalAmount;

  @Column(name = "placed_at")
  private LocalDateTime placedAt;
}
