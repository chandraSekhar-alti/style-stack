package com.example.stylestackapp.auth.repository;

import com.example.stylestackapp.auth.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserSessionRepo extends JpaRepository<UserSession, UUID> {

  Optional<UserSession> findByAccessTokenJti(String jwtId);

  Optional<UserSession> findByRefreshToken(String refreshToken);
}
