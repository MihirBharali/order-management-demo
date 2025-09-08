package com.order.ordermanagement.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.order.ordermanagement.domain.Order;

@Repository
public class OrderDao {

  private final com.order.ordermanagement.repository.OrderRepository repository;

  public OrderDao(com.order.ordermanagement.repository.OrderRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Order save(Order order) {
    return repository.save(order);
  }

  @Transactional(readOnly = true)
  public Optional<Order> findById(Long id) {
    return repository.findById(id);
  }
}
