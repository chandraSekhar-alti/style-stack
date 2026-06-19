package com.example.stylestackapp.auth.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenResponseDto {

    private String accessToken;

    private String tokenType;

}
