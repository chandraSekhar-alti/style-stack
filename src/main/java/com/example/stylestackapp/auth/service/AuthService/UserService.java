package com.example.stylestackapp.auth.service.AuthService;

import com.example.stylestackapp.auth.dto.response.MeResponseDto;
import com.example.stylestackapp.security.service.CustomUserPrincipal;

public interface UserService {

    MeResponseDto getCurrentUser(CustomUserPrincipal principal);

}
