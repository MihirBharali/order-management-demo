package com.order.ordermanagement.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockPaymentController {
    private final Random random = new Random();

    @PostMapping("/v1/payment/validate")
    public ResponseEntity<Boolean> validatePayment(@RequestBody Map<String, String> body) {
        // Randomly return true or false
        boolean result = random.nextBoolean();
        return ResponseEntity.ok(result);
    }
}
