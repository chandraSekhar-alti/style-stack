package com.example.stylestackapp.security.jwt;

import com.example.stylestackapp.auth.entity.User;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractUserName(String token);

    boolean isTokenValid(String token, User user);

    String extractJwtId(String token);
}
