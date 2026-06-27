package com.example.stylestackapp.payment.controller;


import com.example.stylestackapp.common.response.ApiResponse.ApiResponse;
import com.example.stylestackapp.payment.dto.request.CreatePaymentLinkRequest;
import com.example.stylestackapp.payment.dto.response.PaymentLinkResponse;
import com.example.stylestackapp.payment.service.paymentService.PaymentService;
import com.example.stylestackapp.security.service.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/link")
    public ResponseEntity<ApiResponse<PaymentLinkResponse>> generatePaymentLink(
            @Valid @RequestBody CreatePaymentLinkRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) throws Exception {

        PaymentLinkResponse response =
                paymentService.generatePaymentLink(request, principal);

        return ResponseEntity.ok(
                ApiResponse.<PaymentLinkResponse>builder()
                        .success(true)
                        .message("Payment link generated successfully.")
                        .data(response)
                        .timeStamp(LocalDateTime.now())
                        .build());
    }
}
