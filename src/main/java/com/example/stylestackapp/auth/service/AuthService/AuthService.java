package com.example.stylestackapp.auth.service.AuthService;

import com.example.stylestackapp.auth.dto.request.LoginRequestDto;
import com.example.stylestackapp.auth.dto.request.RegisterRequestDto;
import com.example.stylestackapp.auth.dto.response.LoginResponseDto;

public interface AuthService {

    void Register(RegisterRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto requestDto);

    void logout(String accessToken);

}