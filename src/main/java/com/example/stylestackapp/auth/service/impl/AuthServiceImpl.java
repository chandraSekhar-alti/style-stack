package com.example.stylestackapp.auth.service.impl;

import com.example.stylestackapp.auth.dto.request.RegisterRequestDto;
import com.example.stylestackapp.auth.entity.Role;
import com.example.stylestackapp.auth.entity.User;
import com.example.stylestackapp.auth.repository.RoleRepository;
import com.example.stylestackapp.auth.repository.UserRepository;
import com.example.stylestackapp.auth.repository.UserSessionRepository;
import com.example.stylestackapp.auth.service.AuthService.AuthService;
import com.example.stylestackapp.common.enums.RoleName;
import com.example.stylestackapp.common.exceptions.DuplicateResourceException;
import com.example.stylestackapp.common.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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

}
