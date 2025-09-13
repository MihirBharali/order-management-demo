package com.order.ordermanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.ordermanagement.domain.Product;
import com.order.ordermanagement.domain.ProductType;
import com.order.ordermanagement.dto.ProductResponse;
import com.order.ordermanagement.repository.ProductRepository;
import com.order.ordermanagement.util.ApiNames;
import com.order.ordermanagement.util.LogUtil;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final PricingServiceClient pricingServiceClient;

    public ProductService(
            ProductRepository productRepository,
            InventoryServiceClient inventoryServiceClient,
            PricingServiceClient pricingServiceClient) {
        this.productRepository = productRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.pricingServiceClient = pricingServiceClient;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByType(ProductType productType, String apiName, String customerAccountId) {
        long start = System.currentTimeMillis();
        LogUtil.info("Fetching products from DB for type: " + productType, apiName, customerAccountId);
        
        List<Product> products = productRepository.findByProductType(productType);
        List<ProductResponse> response = new ArrayList<>();

        for (Product product : products) {
            Integer inventory = inventoryServiceClient.getInventory(productType, product.getId(), ApiNames.GET_PRODUCTS_BY_TYPE.name(), customerAccountId);
            
            // Only include products with available inventory
            if (inventory > 0) {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setId(product.getId());
                productResponse.setName(product.getName());
                productResponse.setDescription(product.getDescription());
                productResponse.setProductType(product.getProductType());
                productResponse.setSku(product.getSku());
                productResponse.setAvailableQuantity(inventory);
                productResponse.setPrice(pricingServiceClient.getPrice(productType, product.getId(), ApiNames.GET_PRODUCTS_BY_TYPE.name(), customerAccountId));
                response.add(productResponse);
            }
        }

        long duration = System.currentTimeMillis() - start;
        LogUtil.info("Found " + response.size() + " available products", apiName, duration, customerAccountId);
        
        return response;
    }
}
