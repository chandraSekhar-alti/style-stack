package com.example.stylestackapp.auth.service.impl;

import com.example.stylestackapp.auth.dto.response.MeResponseDto;
import com.example.stylestackapp.auth.entity.User;
import com.example.stylestackapp.auth.repository.UserRepository;
import com.example.stylestackapp.auth.service.AuthService.UserService;
import com.example.stylestackapp.security.service.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserPrincipal(user);
    }

    @Override
    @Transactional(readOnly = true)
    public MeResponseDto getCurrentUser(CustomUserPrincipal principal) {
        User user = principal.getUser();

        return MeResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(role ->
                                        role.getName().name())
                                .collect(Collectors.toSet())
                )
                .build();

    }


}
