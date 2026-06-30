package com.example.stylestackapp.payment.repository;

import com.example.stylestackapp.common.enums.PaymentStatus;
import com.example.stylestackapp.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepo extends JpaRepository<Payment, UUID> {

  Optional<Payment> findFirstByOrderIdAndPaymentStatusOrderByCreatedAtDesc(
      UUID orderId, PaymentStatus paymentStatus);
}
