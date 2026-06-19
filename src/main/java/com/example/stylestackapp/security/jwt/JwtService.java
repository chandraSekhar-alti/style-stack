package com.example.stylestackapp.security.jwt;

import com.example.stylestackapp.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractUserName(String token);

    boolean isTokenValid(String token, User user);

    String extractJwtId(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isRefreshTokenValid(String token, User user);
}
