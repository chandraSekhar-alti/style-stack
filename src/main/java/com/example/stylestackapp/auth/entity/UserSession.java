package com.example.stylestackapp.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "access_token_jti", nullable = false)
  private String accessTokenJti;

  @Column(name = "refresh_token", nullable = false)
  private String refreshToken;

  @Column(name = "device_info")
  private String deviceInfo;

  @Column(name = "ip_address")
  private String ipAddress;

  private boolean active;

  @Column(name = "expires_at")
  private LocalDateTime expiresAt;
}
