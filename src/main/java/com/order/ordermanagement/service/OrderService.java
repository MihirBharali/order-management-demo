package com.order.ordermanagement.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.order.ordermanagement.dao.OrderDao;
import com.order.ordermanagement.domain.Order;
import com.order.ordermanagement.dto.CreateOrderRequest;
import com.order.ordermanagement.dto.OrderResponse;

@Service
public class OrderService {

  private final OrderDao orderDao;

  public OrderService(OrderDao orderDao) {
    this.orderDao = orderDao;
  }

  public OrderResponse createOrder(CreateOrderRequest request) {
    Order order = new Order();
    order.setCustomerName(request.getCustomerName());
    order.setTotalAmount(request.getTotalAmount());
    order.setCreatedAt(OffsetDateTime.now());

    Order saved = orderDao.save(order);
    return toResponse(saved);
  }

  public OrderResponse getOrder(Long id) {
    Order order = orderDao.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    return toResponse(order);
  }

  private OrderResponse toResponse(Order order) {
    OrderResponse resp = new OrderResponse();
    resp.setId(order.getId());
    resp.setCustomerName(order.getCustomerName());
    resp.setTotalAmount(order.getTotalAmount());
    resp.setCreatedAt(order.getCreatedAt());
    return resp;
  }
}
