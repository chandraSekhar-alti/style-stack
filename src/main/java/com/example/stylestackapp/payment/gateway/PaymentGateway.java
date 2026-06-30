package com.example.stylestackapp.payment.gateway;

import com.example.stylestackapp.common.enums.PaymentMethod;
import com.example.stylestackapp.order.entity.Order;
import com.example.stylestackapp.payment.dto.response.PaymentLinkResponse;
import com.example.stylestackapp.payment.entity.Payment;

public interface PaymentGateway {

  PaymentMethod getPaymentMethod();

  PaymentLinkResponse createPaymentLink(Order order, Payment payment) throws Exception;
}
