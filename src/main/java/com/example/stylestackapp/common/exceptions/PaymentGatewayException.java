package com.example.stylestackapp.common.exceptions;

import com.razorpay.RazorpayException;

public class PaymentGatewayException extends RuntimeException {
  public PaymentGatewayException(String message, RazorpayException ex) {
    super(message);
  }
}
