package com.order.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.ordermanagement.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {}
