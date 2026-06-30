package com.example.stylestackapp.cart.repository;

import com.example.stylestackapp.cart.entity.Cart;
import com.example.stylestackapp.common.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepo extends JpaRepository<Cart, UUID> {

  Optional<Cart> findByUserIdAndStatus(UUID userId, CartStatus cartStatus);
}
