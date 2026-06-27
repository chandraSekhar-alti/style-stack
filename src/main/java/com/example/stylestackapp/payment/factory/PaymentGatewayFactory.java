package com.example.stylestackapp.payment.factory;

import com.example.stylestackapp.common.enums.PaymentMethod;
import com.example.stylestackapp.common.exceptions.BusinessException;
import com.example.stylestackapp.payment.gateway.PaymentGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentGatewayFactory {

    private final Map<PaymentMethod, PaymentGateway> gateways;

    public PaymentGatewayFactory(List<PaymentGateway> paymentGateways){
        this.gateways = paymentGateways.stream()
                .collect(Collectors.toMap(
                        PaymentGateway :: getPaymentMethod,
                        Function.identity()
                ));
    }

    public PaymentGateway getGateway(PaymentMethod paymentMethod){
        PaymentGateway gateway = gateways.get(paymentMethod);

        if(gateway == null){
            throw new BusinessException("Unsupported payment method");
        }

        return gateway;
    }

}
