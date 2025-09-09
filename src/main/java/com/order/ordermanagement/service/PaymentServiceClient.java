package com.order.ordermanagement.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentServiceClient {
    private static final Logger logger = LogManager.getLogger(PaymentServiceClient.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String PAYMENT_VALIDATE_URL = "http://localhost:8080/v1/payment/validate";

    public boolean isPaymentMethodValid(String paymentMethod) {
        long start = System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = new HashMap<>();
        body.put("paymentMethod", paymentMethod);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Boolean> response = restTemplate.postForEntity(PAYMENT_VALIDATE_URL, entity, Boolean.class);
            long duration = System.currentTimeMillis() - start;
            logger.info("Payment validation API response time: {} ms", duration);
            return Boolean.TRUE.equals(response.getBody());
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            logger.error("Error calling payment validation API ({} ms)", duration, e);
            return false;
        }
    }
}
