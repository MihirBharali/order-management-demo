package com.order.ordermanagement.controller;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.ordermanagement.domain.ProductType;
import com.order.ordermanagement.dto.ProductResponse;
import com.order.ordermanagement.service.ProductService;
import com.order.ordermanagement.util.ApiNames;
import com.order.ordermanagement.util.LogUtil;

@RestController
@RequestMapping("/products/{customerAccountId}")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productType}")
    public ResponseEntity<List<ProductResponse>> getProductsByType(@PathVariable String customerAccountId, @PathVariable ProductType productType) {
        String transactionId = UUID.randomUUID().toString();
        ThreadContext.put("transactionId", transactionId);
        long start = System.currentTimeMillis();
    LogUtil.info("Received get products request for type: " + productType, ApiNames.GET_PRODUCTS_BY_TYPE.name(), customerAccountId);
        try {
            List<ProductResponse> products = productService.getProductsByType(productType, ApiNames.GET_PRODUCTS_BY_TYPE.name(), customerAccountId);
            long duration = System.currentTimeMillis() - start;
            LogUtil.info("Successfully retrieved " + products.size() + " products", ApiNames.GET_PRODUCTS_BY_TYPE.name(), duration, customerAccountId);
            ThreadContext.clearAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            LogUtil.error("Error retrieving products: " + e.getMessage(), ApiNames.GET_PRODUCTS_BY_TYPE.name(), duration, customerAccountId);
            ThreadContext.clearAll();
            return ResponseEntity.internalServerError().build();
        }
    }
}
