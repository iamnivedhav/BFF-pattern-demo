package com.example.orderservice.model;

import lombok.Data;
import java.util.List;

@Data
public class Order {
    private String id;
    private String customerId;
    private String date;
    private String status;
    private List<OrderItem> items;
    private ShippingAddress shippingAddress;
    private double totalAmount;
}