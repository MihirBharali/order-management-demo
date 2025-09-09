package com.order.ordermanagement.domain;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long customerId;

  @Column(nullable = false)
  private OffsetDateTime orderDate;

  @Column(nullable = false)
  private String status;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal totalAmount;

  @Column(nullable = false)
  private String shippingAddress;

  @Column(nullable = false)
  private String billingAddress;

  @ElementCollection
  @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
  private List<OrderItem> items;

  @Column(nullable = false)
  private String paymentMethod;

  @Column(nullable = false)
  private String paymentStatus;

  @Column(nullable = false)
  private String shippingMethod;

  private String trackingNumber;

  @Column(nullable = false)
  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;

  // Getters and setters
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

