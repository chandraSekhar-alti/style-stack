package com.example.stylestackapp.payment.dto.response;

import com.example.stylestackapp.common.enums.PaymentMethod;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentLinkResponse {

    private String paymentUrl;

    private PaymentMethod paymentMethod;

    private String gatewayReferenceId;

}
