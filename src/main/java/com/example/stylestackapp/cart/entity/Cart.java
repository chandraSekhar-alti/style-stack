package com.example.stylestackapp.cart.entity;

import com.example.stylestackapp.auth.entity.AuditableEntity;
import com.example.stylestackapp.auth.entity.User;
import com.example.stylestackapp.common.enums.CartStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends AuditableEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CartStatus status;
}
