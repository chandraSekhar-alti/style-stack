package com.example.stylestackapp.payment.dto.request;

import com.example.stylestackapp.common.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePaymentLinkRequest {

    private UUID orderId;

    private PaymentMethod paymentMethod;

}
