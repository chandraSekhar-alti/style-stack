package com.example.stylestackapp.payment.service.impl;

import com.example.stylestackapp.common.enums.OrderStatus;
import com.example.stylestackapp.common.enums.PaymentStatus;
import com.example.stylestackapp.common.exceptions.BusinessException;
import com.example.stylestackapp.common.exceptions.ResourceNotFoundException;
import com.example.stylestackapp.common.exceptions.UnauthorizedException;
import com.example.stylestackapp.order.entity.Order;
import com.example.stylestackapp.order.repository.OrderRepo;
import com.example.stylestackapp.payment.dto.request.CreatePaymentLinkRequest;
import com.example.stylestackapp.payment.dto.response.PaymentLinkResponse;
import com.example.stylestackapp.payment.entity.Payment;
import com.example.stylestackapp.payment.factory.PaymentGatewayFactory;
import com.example.stylestackapp.payment.gateway.PaymentGateway;
import com.example.stylestackapp.payment.repository.PaymentRepo;
import com.example.stylestackapp.payment.service.paymentService.PaymentService;
import com.example.stylestackapp.security.service.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepo orderRepo;
    private final PaymentRepo paymentRepo;
    private final PaymentGatewayFactory paymentGatewayFactory;

    @Override
    public PaymentLinkResponse generatePaymentLink(
            CreatePaymentLinkRequest request,
            CustomUserPrincipal principal
    ) throws Exception {

        Order order = orderRepo.findById(request.getOrderId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Order not found")
                );

        //Validate Owner
        if (!order.getUser().getId().equals(principal.getUserId())) {
            throw new UnauthorizedException("You are not authorized to access this order.");
        }

        //Validate Order Status
        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new BusinessException("Payment link can only be generated for pending orders.");
        }

        Optional<Payment> existingPayment = paymentRepo
                .findFirstByOrderIdAndPaymentStatusOrderByCreatedAtDesc(
                        order.getId(),
                        PaymentStatus.PENDING
                );

        if (existingPayment.isPresent()) {
            return PaymentLinkResponse.builder()
                    .paymentUrl(existingPayment.get().getPaymentLink())
                    .gatewayReferenceId(existingPayment.get().getGatewayReferenceId())
                    .paymentMethod(existingPayment.get().getPaymentMethod())
                    .build();
        }

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .amount(order.getTotalAmount())
                .build();

        paymentRepo.save(payment);

        PaymentGateway gateway = paymentGatewayFactory.getGateway(
                request.getPaymentMethod()
        );

        PaymentLinkResponse response = gateway.createPaymentLink(order, payment);

        //Update Payment
        payment.setGatewayReferenceId(response.getGatewayReferenceId());
        payment.setPaymentLink(response.getPaymentUrl());
        paymentRepo.save(payment);

        return response;
    }
}
