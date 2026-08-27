package com.example.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentClient {

    private final RestTemplate restTemplate;
    private final String paymentServiceBaseUrl;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public PaymentClient(
            RestTemplate restTemplate,
            CircuitBreakerFactory<?, ?> circuitBreakerFactory,
            @Value("${payment.service.base-url}") String paymentServiceBaseUrl) {

        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.paymentServiceBaseUrl = paymentServiceBaseUrl;
    }

    public String processPayment(String mode) {

        String url = paymentServiceBaseUrl
                + "/api/payments?mode=" + mode;

        System.out.println("Calling Payment Service: " + url);

        var circuitBreaker =
                circuitBreakerFactory.create("paymentService");

        return circuitBreaker.run(
                () -> restTemplate.getForObject(
                        url,
                        String.class
                ),
                this::paymentFallback
        );
    }

    private String paymentFallback(Throwable throwable) {

        System.out.println(
                "Payment Service unavailable: "
                        + throwable.getMessage()
        );

        return "Payment Service is temporarily unavailable. Please try again later.";
    }
}