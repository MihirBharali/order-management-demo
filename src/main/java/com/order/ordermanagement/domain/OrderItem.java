package com.order.ordermanagement.domain;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItem {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
