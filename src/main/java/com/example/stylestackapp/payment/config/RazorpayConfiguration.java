package com.example.stylestackapp.payment.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfiguration {

  @Value("${razorpay.key-id}") // environment variables
  private String razorpayKey;

  @Value("${razorpay.key-secret}")
  private String razorpaySecret;

  @Bean
  public RazorpayClient getRazorpayClient() throws RazorpayException {
    return new RazorpayClient(razorpayKey, razorpaySecret);
  }
}
