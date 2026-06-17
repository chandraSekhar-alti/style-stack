package com.example.stylestackapp.auth.service.AuthService;

import com.example.stylestackapp.auth.dto.request.RegisterRequestDto;

public interface AuthService {

    void Register(RegisterRequestDto requestDto);

}