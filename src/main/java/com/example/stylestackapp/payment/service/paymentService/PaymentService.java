package com.example.stylestackapp.payment.service.paymentService;

import com.example.stylestackapp.payment.dto.request.CreatePaymentLinkRequest;
import com.example.stylestackapp.payment.dto.response.PaymentLinkResponse;
import com.example.stylestackapp.security.service.CustomUserPrincipal;

public interface PaymentService {

  PaymentLinkResponse generatePaymentLink(
      CreatePaymentLinkRequest request, CustomUserPrincipal principal) throws Exception;
}
