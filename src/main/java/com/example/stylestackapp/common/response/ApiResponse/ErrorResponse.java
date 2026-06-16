package com.example.stylestackapp.common.response.ApiResponse;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private boolean success;

    private String message;

    private List<String> errors;

    private LocalDateTime timestamp;
}
