package com.example.stylestackapp.auth.repository;

import com.example.stylestackapp.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    @Query("""
       SELECT u
       FROM User u
       LEFT JOIN FETCH u.roles
       WHERE u.email = :email
       """)
    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
}
