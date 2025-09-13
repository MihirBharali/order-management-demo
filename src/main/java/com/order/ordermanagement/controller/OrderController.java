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
@RequestMapping("/orders/{customerAccountId}")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<OrderResponse> create(@PathVariable String customerAccountId, @Valid @RequestBody CreateOrderRequest request) {
    String transactionId = UUID.randomUUID().toString();
    ThreadContext.put("transactionId", transactionId);
  LogUtil.info("Received create order request", ApiNames.CREATE_ORDER.name(), customerAccountId);
    OrderResponse response = orderService.createOrder(request, customerAccountId);
  LogUtil.info("Order created successfully", ApiNames.CREATE_ORDER.name(), customerAccountId);
    ThreadContext.clearAll();
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getById(@PathVariable String customerAccountId, @PathVariable Long id) {
    String transactionId = UUID.randomUUID().toString();
    ThreadContext.put("transactionId", transactionId);
  LogUtil.info("Received get order by id request", ApiNames.GET_ORDER_BY_ID.name(), customerAccountId);
    OrderResponse response = orderService.getOrder(id, customerAccountId);
  LogUtil.info("Order fetched successfully", ApiNames.GET_ORDER_BY_ID.name(), customerAccountId);
    ThreadContext.clearAll();
    return ResponseEntity.ok(response);
  }

  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<String> handleNotFound(OrderNotFoundException ex, @PathVariable Long customerAccountId) {
    String transactionId = UUID.randomUUID().toString();
    ThreadContext.put("transactionId", transactionId);
    long start = System.currentTimeMillis();
  LogUtil.error("Order not found: " + ex.getMessage(), ApiNames.GET_ORDER_BY_ID.name(), customerAccountId.toString());
    long duration = System.currentTimeMillis() - start;
  LogUtil.error("Order not found response: " + ex.getMessage(), ApiNames.GET_ORDER_BY_ID.name(), duration, customerAccountId.toString());
    ThreadContext.clearAll();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
