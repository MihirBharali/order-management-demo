package com.order.ordermanagement.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.order.ordermanagement.domain.OrderItem;

public class OrderResponse {
  private Long id;
  private Long customerId;
  private OffsetDateTime orderDate;
  private String status;
  private BigDecimal totalAmount;
  private String shippingAddress;
  private String billingAddress;
  private List<OrderItem> items;
  private String paymentMethod;
  private String paymentStatus;
  private String shippingMethod;
  private String trackingNumber;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Long getCustomerId() { return customerId; }
  public void setCustomerId(Long customerId) { this.customerId = customerId; }

  public OffsetDateTime getOrderDate() { return orderDate; }
  public void setOrderDate(OffsetDateTime orderDate) { this.orderDate = orderDate; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public BigDecimal getTotalAmount() { return totalAmount; }
  public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

  public String getShippingAddress() { return shippingAddress; }
  public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

  public String getBillingAddress() { return billingAddress; }
  public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

  public List<OrderItem> getItems() { return items; }
  public void setItems(List<OrderItem> items) { this.items = items; }

  public String getPaymentMethod() { return paymentMethod; }
  public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

  public String getPaymentStatus() { return paymentStatus; }
  public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

  public String getShippingMethod() { return shippingMethod; }
  public void setShippingMethod(String shippingMethod) { this.shippingMethod = shippingMethod; }

  public String getTrackingNumber() { return trackingNumber; }
  public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
