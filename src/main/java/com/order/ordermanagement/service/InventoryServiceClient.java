package com.order.ordermanagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.order.ordermanagement.domain.ProductType;
import com.order.ordermanagement.util.LogUtil;
import com.order.ordermanagement.util.OperationNames;

@Component
public class InventoryServiceClient {
    private static final Logger logger = LogManager.getLogger(InventoryServiceClient.class);
    private final RestTemplate restTemplate;
    private final String inventoryServiceUrl;

    public InventoryServiceClient(
            @Value("${service.inventory.url:http://inventory-service}") String inventoryServiceUrl) {
        this.restTemplate = new RestTemplate();
        this.inventoryServiceUrl = inventoryServiceUrl;
    }

    public Integer getInventory(ProductType productType, Long productId, String apiName, String customerAccountId) {
        String url = String.format("%s/inventory/%s/%d", inventoryServiceUrl, productType.name().toLowerCase(), productId);
        long start = System.currentTimeMillis();
        try {
            Integer inventory = restTemplate.getForObject(url, Integer.class);
            long duration = System.currentTimeMillis() - start;
            LogUtil.logClientResponseTime("Inventory Service API response time", apiName, duration, customerAccountId, OperationNames.PRICING_SERVICE_API_CALL);
            return inventory != null ? inventory : 0;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            LogUtil.error("Error calling inventory service: " + e.getMessage(), "GET_INVENTORY", duration, customerAccountId);
            return 0;
        }
    }
}
