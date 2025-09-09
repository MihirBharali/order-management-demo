

package com.order.ordermanagement.controller;

import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.ordermanagement.dto.CreateOrderRequest;
import com.order.ordermanagement.dto.OrderResponse;
import com.order.ordermanagement.service.OrderNotFoundException;
import com.order.ordermanagement.service.OrderService;
import com.order.ordermanagement.util.ApiNames;
import com.order.ordermanagement.util.LogUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
    String transactionId = UUID.randomUUID().toString();
    ThreadContext.put("transactionId", transactionId);
    long start = System.currentTimeMillis();
    LogUtil.info("Received create order request", ApiNames.CREATE_ORDER);
    try {
      OrderResponse response = orderService.createOrder(request);
      long duration = System.currentTimeMillis() - start;
      LogUtil.info("Order created successfully: " + response, ApiNames.CREATE_ORDER, duration);
      ThreadContext.clearAll();
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (Exception ex) {
      long duration = System.currentTimeMillis() - start;
      LogUtil.error("Order creation failed: " + ex.getMessage(), ApiNames.CREATE_ORDER, duration);
      ThreadContext.clearAll();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
    String transactionId = UUID.randomUUID().toString();
    ThreadContext.put("transactionId", transactionId);
    long start = System.currentTimeMillis();
    LogUtil.info("Received get order by id request", ApiNames.GET_ORDER_BY_ID);
    try {
      OrderResponse response = orderService.getOrder(id);
      long duration = System.currentTimeMillis() - start;
      LogUtil.info("Order fetched successfully: " + response, ApiNames.GET_ORDER_BY_ID, duration);
      ThreadContext.clearAll();
      return ResponseEntity.ok(response);
    } catch (Exception ex) {
      long duration = System.currentTimeMillis() - start;
      LogUtil.error("Order fetch failed: " + ex.getMessage(), ApiNames.GET_ORDER_BY_ID, duration);
      ThreadContext.clearAll();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<String> handleNotFound(OrderNotFoundException ex) {
    String transactionId = UUID.randomUUID().toString();
    ThreadContext.put("transactionId", transactionId);
    long start = System.currentTimeMillis();
    LogUtil.error("Order not found: " + ex.getMessage(), ApiNames.GET_ORDER_BY_ID);
    long duration = System.currentTimeMillis() - start;
    LogUtil.error("Order not found response: " + ex.getMessage(), ApiNames.GET_ORDER_BY_ID, duration);
    ThreadContext.clearAll();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
