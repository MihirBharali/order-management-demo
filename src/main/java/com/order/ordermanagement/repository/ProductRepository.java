package com.order.ordermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.ordermanagement.domain.Product;
import com.order.ordermanagement.domain.ProductType;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductType(ProductType productType);
}
