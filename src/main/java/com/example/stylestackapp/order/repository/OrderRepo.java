package com.example.stylestackapp.order.repository;

import com.example.stylestackapp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByUserId(UUID userId);
}
