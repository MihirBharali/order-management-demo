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
  public Order save(Order order, com.order.ordermanagement.util.ApiNames apiName, String customerAccountId) {
    long start = System.currentTimeMillis();
    try {
      Order saved = repository.save(order);
      long duration = System.currentTimeMillis() - start;
      LogUtil.info("Order saved successfully: " + saved, apiName.name(), duration, customerAccountId);
      return saved;
    } catch (Exception e) {
      long duration = System.currentTimeMillis() - start;
      LogUtil.error("Failed to save order: " + e.getMessage(), apiName.name(), duration, customerAccountId);
      throw new OrderSaveException("Failed to save order: " + e.getMessage());
    }
  }

  @Transactional(readOnly = true)
  public Order findByIdOrThrow(Long id, com.order.ordermanagement.util.ApiNames apiName, String customerAccountId) {
    long start = System.currentTimeMillis();
    try {
      Order found = repository.findById(id)
        .orElseThrow(() -> new OrderNotFoundInDaoException("Order not found in DAO for id: " + id));
      long duration = System.currentTimeMillis() - start;
      LogUtil.info("Order found: " + found, apiName.name(), duration, customerAccountId);
      return found;
    } catch (Exception e) {
      long duration = System.currentTimeMillis() - start;
      LogUtil.error("Error finding order in DAO: " + e.getMessage(), apiName.name(), duration, customerAccountId);
      throw new OrderNotFoundInDaoException("Error finding order in DAO: " + e.getMessage());
    }
  }
}
