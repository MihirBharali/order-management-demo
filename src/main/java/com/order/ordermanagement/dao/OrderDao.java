package com.order.ordermanagement.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.order.ordermanagement.domain.Order;
import com.order.ordermanagement.util.LogUtil;

@Repository
public class OrderDao {

  private final com.order.ordermanagement.repository.OrderRepository repository;

  public OrderDao(com.order.ordermanagement.repository.OrderRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Order save(Order order, String apiName) {
    long start = System.currentTimeMillis();
    try {
      Order saved = repository.save(order);
      long duration = System.currentTimeMillis() - start;
      LogUtil.info("Order saved successfully: " + saved, apiName, duration);
      return saved;
    } catch (Exception e) {
      long duration = System.currentTimeMillis() - start;
      LogUtil.error("Failed to save order: " + e.getMessage(), apiName, duration);
      throw new OrderSaveException("Failed to save order: " + e.getMessage());
    }
  }

  @Transactional(readOnly = true)
  public Order findByIdOrThrow(Long id, String apiName) {
    long start = System.currentTimeMillis();
    try {
      Order found = repository.findById(id)
        .orElseThrow(() -> new OrderNotFoundInDaoException("Order not found in DAO for id: " + id));
      long duration = System.currentTimeMillis() - start;
      LogUtil.info("Order found: " + found, apiName, duration);
      return found;
    } catch (Exception e) {
      long duration = System.currentTimeMillis() - start;
      LogUtil.error("Error finding order in DAO: " + e.getMessage(), apiName, duration);
      throw new OrderNotFoundInDaoException("Error finding order in DAO: " + e.getMessage());
    }
  }
}
