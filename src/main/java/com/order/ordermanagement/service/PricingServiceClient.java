package com.order.ordermanagement.service;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.order.ordermanagement.domain.ProductType;
import com.order.ordermanagement.util.LogUtil;
import com.order.ordermanagement.util.OperationNames;

@Component
public class PricingServiceClient {
    private static final Logger logger = LogManager.getLogger(PricingServiceClient.class);
    private final RestTemplate restTemplate;
    private final String pricingServiceUrl;

    public PricingServiceClient(
            @Value("${service.pricing.url:http://pricing-service}") String pricingServiceUrl) {
        this.restTemplate = new RestTemplate();
        this.pricingServiceUrl = pricingServiceUrl;
    }

    public BigDecimal getPrice(ProductType productType, Long productId, String name, String customerAccountId) {
        String url = String.format("%s/price/%s/%d", pricingServiceUrl, productType.name().toLowerCase(), productId);
        long start = System.currentTimeMillis();
        try {
            BigDecimal price = restTemplate.getForObject(url, BigDecimal.class);
            long duration = System.currentTimeMillis() - start;
            LogUtil.logClientResponseTime("Pricing Service API response time", name, duration, customerAccountId, OperationNames.PRICING_SERVICE_API_CALL);
            return price != null ? price : BigDecimal.ZERO;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            LogUtil.error("Error calling pricing service: " + e.getMessage(), "GET_PRICE", duration, customerAccountId);
            return BigDecimal.ZERO;
        }
    }
}
