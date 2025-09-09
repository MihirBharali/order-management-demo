package com.order.ordermanagement.dao;

public class OrderNotFoundInDaoException extends RuntimeException {
    public OrderNotFoundInDaoException(String message) {
        super(message);
    }
}
