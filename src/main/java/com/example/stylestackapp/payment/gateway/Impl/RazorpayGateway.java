package com.example.stylestackapp.payment.gateway.Impl;

import com.example.stylestackapp.auth.entity.User;
import com.example.stylestackapp.common.enums.PaymentMethod;
import com.example.stylestackapp.common.exceptions.PaymentGatewayException;
import com.example.stylestackapp.order.entity.Order;
import com.example.stylestackapp.payment.dto.response.PaymentLinkResponse;
import com.example.stylestackapp.payment.entity.Payment;
import com.example.stylestackapp.payment.gateway.PaymentGateway;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class RazorpayGateway implements PaymentGateway {

    private final RazorpayClient razorpayClient;
    private final String callbackUrl = "https://www.google.com/";

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.RAZORPAY;
    }

    @Override
    public PaymentLinkResponse createPaymentLink(Order order, Payment payment) {

        JSONObject request = buildPaymentRequest(order);
        PaymentLink paymentLink = createPaymentLinkFromGateway(request);

        log.info(
                "Payment link generated successfully. Order={}, GatewayReference={}",
                order.getOrderNumber(),
                paymentLink.get("id"));

        return buildPaymentResponse(paymentLink);
    }


    private JSONObject buildPaymentRequest(Order order) {
        JSONObject request = new JSONObject();

        request.put("amount", convertToPaise(order.getTotalAmount()));
        request.put("currency", "INR");
        request.put("accept_partial", false);
        request.put("reference_id", order.getOrderNumber());
        request.put("description", "Payment for Order " + order.getOrderNumber());
        request.put("expire_by", getExpiryTime());
        request.put("customer", buildCustomer(order));
        request.put("notify", buildNotification());
        request.put("notes", buildNotes(order));
        request.put("callback_url", callbackUrl);
        request.put("callback_method", "get");

        return request;

    }

    private JSONObject buildCustomer(Order order) {
        User user = order.getUser();

        JSONObject customer = new JSONObject();

        customer.put("name", user.getFirstName() + " " + user.getLastName());
        customer.put("contact", user.getPhoneNumber());
        customer.put("email", user.getEmail());

        return customer;

    }

    private JSONObject buildNotification() {
        JSONObject notify = new JSONObject();

        notify.put("sms", true);
        notify.put("email", true);

        return notify;
    }

    private JSONObject buildNotes(Order order) {
        JSONObject notes = new JSONObject();

        notes.put("orderNumber", order.getOrderNumber());
        notes.put("customerId", order.getUser().getId().toString());

        return notes;
    }


    //Gateway
    private PaymentLink createPaymentLinkFromGateway(
            JSONObject request) {

        try {
            return razorpayClient
                    .paymentLink
                    .create(request);

        } catch (RazorpayException ex) {
            log.error("Razorpay Error", ex);
            throw new PaymentGatewayException(
                    ex.getMessage(),
                    ex);
//            throw new PaymentGatewayException("Unable to generate Razorpay payment link.", ex);

        }
    }

    private PaymentLinkResponse buildPaymentResponse(PaymentLink paymentLink) {

        return PaymentLinkResponse.builder()
                .paymentUrl(paymentLink.get("short_url"))
                .gatewayReferenceId(paymentLink.get("id"))
                .paymentMethod(PaymentMethod.RAZORPAY)
                .build();
    }

    //Utility
    private long convertToPaise(BigDecimal amount) {
        return amount.multiply(
                BigDecimal.valueOf(100)
        ).longValue();
    }

    private long getExpiryTime() {
        return Instant.now()
                .plus(Duration.ofMinutes(30))
                .getEpochSecond();
    }

}
