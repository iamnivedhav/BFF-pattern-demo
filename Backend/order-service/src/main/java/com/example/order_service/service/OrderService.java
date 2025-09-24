package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
}