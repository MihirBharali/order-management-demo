package com.order.ordermanagement.service;


import java.time.OffsetDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.ordermanagement.dao.OrderDao;
import com.order.ordermanagement.domain.Order;
import com.order.ordermanagement.dto.CreateOrderRequest;
import com.order.ordermanagement.dto.OrderResponse;

@Service
public class OrderService {
  private static final Logger logger = LogManager.getLogger(OrderService.class);

  private final OrderDao orderDao;
  private final PaymentServiceClient paymentServiceClient;

  @Autowired
  public OrderService(OrderDao orderDao, PaymentServiceClient paymentServiceClient) {
    this.orderDao = orderDao;
    this.paymentServiceClient = paymentServiceClient;
  }

  public OrderResponse createOrder(CreateOrderRequest request) {
    logger.info("Creating new order for customerId: {}", request.getCustomerId());
    validateRequest(request);
    logger.debug("Validated order request: {}", request);

    // Validate payment method via external API
    if (!paymentServiceClient.isPaymentMethodValid(request.getPaymentMethod())) {
      logger.error("Payment method validation failed for: {}", request.getPaymentMethod());
      throw new ValidationException("Invalid payment method");
    }

    Order order = new Order();
    order.setCustomerId(request.getCustomerId());
    order.setShippingAddress(request.getShippingAddress());
    order.setBillingAddress(request.getBillingAddress());
    order.setItems(request.getItems());
    order.setPaymentMethod(request.getPaymentMethod());
    order.setShippingMethod(request.getShippingMethod());
    order.setTotalAmount(request.getTotalAmount());
    order.setOrderDate(OffsetDateTime.now());
    order.setStatus("PENDING");
    order.setCreatedAt(OffsetDateTime.now());

    Order saved = orderDao.save(order);
    logger.info("Order created with id: {}", saved.getId());
    return toResponse(saved);
  }

  // Payment validation logic moved to PaymentServiceClient

  public OrderResponse getOrder(Long id) {
    logger.info("Fetching order with id: {}", id);
  Order order = orderDao.findByIdOrThrow(id);
    logger.debug("Order found: {}", order);
    return toResponse(order);
  }

  private void validateRequest(CreateOrderRequest request) {
    if (request.getCustomerId() == null) {
    logger.error("Validation failed: customerId is required");
      throw new ValidationException("customerId is required");
    }
    if (isBlank(request.getShippingAddress())) {
    logger.error("Validation failed: shippingAddress is required");
      throw new ValidationException("shippingAddress is required");
    }
    if (isBlank(request.getBillingAddress())) {
    logger.error("Validation failed: billingAddress is required");
      throw new ValidationException("billingAddress is required");
    }
    if (request.getItems() == null || request.getItems().isEmpty()) {
    logger.error("Validation failed: items are required");
      throw new ValidationException("items are required");
    }
    if (isBlank(request.getPaymentMethod())) {
    logger.error("Validation failed: paymentMethod is required");
      throw new ValidationException("paymentMethod is required");
    }
    if (isBlank(request.getShippingMethod())) {
    logger.error("Validation failed: shippingMethod is required");
      throw new ValidationException("shippingMethod is required");
    }
    if (request.getTotalAmount() == null || request.getTotalAmount().signum() <= 0) {
    logger.error("Validation failed: totalAmount must be positive");
      throw new ValidationException("totalAmount must be positive");
    }
  }

  private boolean isBlank(String str) {
    return str == null || str.trim().isEmpty();
  }

  private OrderResponse toResponse(Order order) {
    OrderResponse resp = new OrderResponse();
    resp.setId(order.getId());
    resp.setCustomerId(order.getCustomerId());
    resp.setOrderDate(order.getOrderDate());
    resp.setStatus(order.getStatus());
    resp.setTotalAmount(order.getTotalAmount());
    resp.setShippingAddress(order.getShippingAddress());
    resp.setBillingAddress(order.getBillingAddress());
    resp.setItems(order.getItems());
    resp.setPaymentMethod(maskPaymentMethod(order.getPaymentMethod()));
    resp.setPaymentStatus(order.getPaymentStatus());
    resp.setShippingMethod(order.getShippingMethod());
    resp.setTrackingNumber(order.getTrackingNumber());
    resp.setCreatedAt(order.getCreatedAt());
    resp.setUpdatedAt(order.getUpdatedAt());
    return resp;
  }

  private String maskPaymentMethod(String paymentMethod) {
    if (paymentMethod == null) return null;
    int maskLength = Math.min(10, paymentMethod.length());
    StringBuilder masked = new StringBuilder();
    for (int i = 0; i < maskLength; i++) {
      masked.append('*');
    }
    masked.append(paymentMethod.substring(maskLength));
    return masked.toString();
  }
}

