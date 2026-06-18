package com.example.stylestackapp.auth.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private String email;

    private String accessToken;

    private String refreshToken;

    private String tokenType;

}
