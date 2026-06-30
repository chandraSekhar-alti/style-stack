package com.example.stylestackapp.auth.dto.response;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeResponseDto {

  private UUID id;

  private String firstName;

  private String lastName;

  private String email;

  private String phoneNumber;

  private Set<String> roles;
}
