package com.order.ordermanagement.dto;

import java.math.BigDecimal;
import java.util.List;

import com.order.ordermanagement.domain.OrderItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateOrderRequest {
  @NotNull
  private Long customerId;

  @NotNull
  private String shippingAddress;

  @NotNull
  private String billingAddress;

  @NotNull
  private List<OrderItem> items;

  @NotNull
  private String paymentMethod;

  @NotNull
  private String shippingMethod;

  @NotNull
  @Positive
  private BigDecimal totalAmount;

  // Getters and setters
  public Long getCustomerId() { return customerId; }
  public void setCustomerId(Long customerId) { this.customerId = customerId; }

  public String getShippingAddress() { return shippingAddress; }
  public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

  public String getBillingAddress() { return billingAddress; }
  public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

  public List<OrderItem> getItems() { return items; }
  public void setItems(List<OrderItem> items) { this.items = items; }

  public String getPaymentMethod() { return paymentMethod; }
  public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

  public String getShippingMethod() { return shippingMethod; }
  public void setShippingMethod(String shippingMethod) { this.shippingMethod = shippingMethod; }

  public BigDecimal getTotalAmount() { return totalAmount; }
  public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
