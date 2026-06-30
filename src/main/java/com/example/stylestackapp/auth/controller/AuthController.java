package com.example.stylestackapp.auth.controller;

import com.example.stylestackapp.auth.dto.request.LoginRequestDto;
import com.example.stylestackapp.auth.dto.request.RefreshTokenRequestDto;
import com.example.stylestackapp.auth.dto.request.RegisterRequestDto;
import com.example.stylestackapp.auth.dto.response.LoginResponseDto;
import com.example.stylestackapp.auth.dto.response.RefreshTokenResponseDto;
import com.example.stylestackapp.auth.service.AuthService.AuthService;
import com.example.stylestackapp.common.exceptions.UnauthorizedException;
import com.example.stylestackapp.common.response.ApiResponse.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Void>> register(
      @Valid @RequestBody RegisterRequestDto requestDto) {
    authService.Register(requestDto);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ApiResponse.<Void>builder()
                .success(true)
                .message("User registered successfully")
                .timeStamp(LocalDateTime.now())
                .build());
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponseDto>> login(
      @Valid @RequestBody LoginRequestDto requestDto) {
    LoginResponseDto loginResponse = authService.login(requestDto);

    return ok(
        ApiResponse.<LoginResponseDto>builder()
            .success(true)
            .message("User logged in successfully")
            .timeStamp(LocalDateTime.now())
            .data(loginResponse)
            .build());
  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new UnauthorizedException("Authorization header is missing or invalid");
    }
    String token = authHeader.substring(7);

    authService.logout(token);

    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .success(true)
            .message("User logged out successfully")
            .timeStamp(LocalDateTime.now())
            .build());
  }

  @PostMapping("/refresh")
  public ResponseEntity<ApiResponse<RefreshTokenResponseDto>> refresh(
      @Valid @RequestBody RefreshTokenRequestDto requestDto) {
    RefreshTokenResponseDto response = authService.refreshToken(requestDto);
    return ResponseEntity.ok(
        ApiResponse.<RefreshTokenResponseDto>builder()
            .success(true)
            .message("Token Refreshed successfully")
            .data(response)
            .timeStamp(LocalDateTime.now())
            .build());
  }
}
