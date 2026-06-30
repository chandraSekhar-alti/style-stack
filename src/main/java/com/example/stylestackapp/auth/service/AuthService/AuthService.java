package com.example.stylestackapp.auth.service.AuthService;

import com.example.stylestackapp.auth.dto.request.LoginRequestDto;
import com.example.stylestackapp.auth.dto.request.RefreshTokenRequestDto;
import com.example.stylestackapp.auth.dto.request.RegisterRequestDto;
import com.example.stylestackapp.auth.dto.response.LoginResponseDto;
import com.example.stylestackapp.auth.dto.response.RefreshTokenResponseDto;

public interface AuthService {

  void Register(RegisterRequestDto requestDto);

  LoginResponseDto login(LoginRequestDto requestDto);

  void logout(String accessToken);

  RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto requestDto);
}
