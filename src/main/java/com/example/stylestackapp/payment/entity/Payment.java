package com.example.stylestackapp.payment.entity;

import com.example.stylestackapp.auth.entity.AuditableEntity;
import com.example.stylestackapp.common.enums.PaymentMethod;
import com.example.stylestackapp.common.enums.PaymentStatus;
import com.example.stylestackapp.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "payments",
    indexes = {
      @Index(name = "idx_payment_order", columnList = "order_id"),
      @Index(name = "idx_payment_status", columnList = "payment_status")
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends AuditableEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method", nullable = false, length = 30)
  private PaymentMethod paymentMethod;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status", nullable = false, length = 30)
  private PaymentStatus paymentStatus;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  @Column(name = "gateway_reference_id", unique = true)
  private String gatewayReferenceId;

  @Column(name = "payment_link", length = 1000)
  private String paymentLink;

  @Column(name = "completed_at")
  private LocalDateTime completedAt;
}
