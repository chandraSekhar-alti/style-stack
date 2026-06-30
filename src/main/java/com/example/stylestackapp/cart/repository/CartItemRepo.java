package com.example.stylestackapp.cart.repository;

import com.example.stylestackapp.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepo extends JpaRepository<CartItem, UUID> {

  List<CartItem> findByCartId(UUID cartId);

  Optional<CartItem> findByCartIdAndProductId(UUID cartId, UUID productId);
}
