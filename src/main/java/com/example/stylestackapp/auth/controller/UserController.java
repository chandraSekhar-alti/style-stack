package com.example.stylestackapp.auth.controller;

import com.example.stylestackapp.auth.dto.response.MeResponseDto;
import com.example.stylestackapp.auth.service.AuthService.UserService;
import com.example.stylestackapp.common.response.ApiResponse.ApiResponse;
import com.example.stylestackapp.security.service.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<MeResponseDto>> me(
      @AuthenticationPrincipal CustomUserPrincipal principal) {
    MeResponseDto response = userService.getCurrentUser(principal);

    return ResponseEntity.ok(
        ApiResponse.<MeResponseDto>builder()
            .success(true)
            .message("User details fetched successfully")
            .data(response)
            .timeStamp(LocalDateTime.now())
            .build());
  }
}
