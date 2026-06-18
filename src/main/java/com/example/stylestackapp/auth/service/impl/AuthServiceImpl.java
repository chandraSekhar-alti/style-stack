package com.example.stylestackapp.auth.service.impl;

import com.example.stylestackapp.auth.dto.request.LoginRequestDto;
import com.example.stylestackapp.auth.dto.request.RegisterRequestDto;
import com.example.stylestackapp.auth.dto.response.LoginResponseDto;
import com.example.stylestackapp.auth.entity.Role;
import com.example.stylestackapp.auth.entity.User;
import com.example.stylestackapp.auth.entity.UserSession;
import com.example.stylestackapp.auth.repository.RoleRepository;
import com.example.stylestackapp.auth.repository.UserRepository;
import com.example.stylestackapp.auth.repository.UserSessionRepository;
import com.example.stylestackapp.auth.service.AuthService.AuthService;
import com.example.stylestackapp.common.enums.RoleName;
import com.example.stylestackapp.common.exceptions.DuplicateResourceException;
import com.example.stylestackapp.common.exceptions.ResourceNotFoundException;
import com.example.stylestackapp.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void Register(RegisterRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateResourceException("User with email " + requestDto.getEmail() + " already exists");
        }

        Role customerRole = roleRepository.
                findByName(
                        RoleName.ROLE_CUSTOMER
                ).orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User savedUser = User.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())
                .password(
                        passwordEncoder.encode(requestDto.getPassword()))
                .enabled(true)
                .emailVerified(false)
                .accountNonLocked(true)
                .build();

        savedUser.getRoles().add(customerRole);

        userRepository.save(savedUser);
    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )
        );

        Optional<User> optionalUser = userRepository.findByEmail(requestDto.getEmail());

        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with email " + requestDto.getEmail() + " not found");
        }

        User user = optionalUser.get();

        String accessToken = jwtService.generateAccessToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        UserSession userSession =
                UserSession.builder()
                        .user(user)
                        .accessTokenJti(
                                jwtService.extractJwtId(accessToken)
                        )
                        .refreshToken(refreshToken)
                        .active(true)
                        .expiresAt(
                                LocalDateTime.now().plusDays(7)
                        )
                        .build();

        userSessionRepository.save(userSession);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .email(user.getEmail())
                .build();

    }


    @Override
    @Transactional
    public void logout(String accessToken){
        String jti = jwtService.extractJwtId(accessToken);

        UserSession userSession = userSessionRepository.findByAccessTokenJti(jti)
                .orElseThrow(() -> new ResourceNotFoundException("User session not found"));

        userSession.setActive(false);

        userSessionRepository.save(userSession);
    }
}
