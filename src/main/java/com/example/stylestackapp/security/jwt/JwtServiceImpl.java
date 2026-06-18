package com.example.stylestackapp.security.jwt;

import com.example.stylestackapp.auth.entity.Role;
import com.example.stylestackapp.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey =
                Keys.hmacShaKeyFor(
                        secret.getBytes(StandardCharsets.UTF_8)
                );
    }

    @Override
    public String generateAccessToken(User user) {

        Map<String, Object> claims = new HashMap<>();

        String jwtId = UUID.randomUUID().toString();

        claims.put(
                "roles",
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toList()
        );

        claims.put("jwtId", jwtId);

        return buildToken(
                claims,
                user.getEmail(),
                jwtExpiration
        );
    }

    @Override
    public String generateRefreshToken(User user) {

        return buildToken(
                new HashMap<>(),
                user.getEmail(),
                refreshExpiration
        );
    }

    @Override
    public String extractUserName(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    @Override
    public boolean isTokenValid(String token, User user) {

        String username = extractUserName(token);

        return username.equals(user.getEmail())
                && !isTokenExpired(token);
    }

    private String buildToken(Map<String, Object> claims, String subject, long expiration) {

        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    private boolean isTokenExpired(String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String extractJwtId(String token) {
        return extractAllClaims(token).get("jwtId", String.class);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails){
        return extractUserName(token)
                .equals(
                        userDetails.getUsername())
                && !isTokenExpired(token);
    }
}