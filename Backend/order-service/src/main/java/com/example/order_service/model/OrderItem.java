package com.example.orderservice.model;

import lombok.Data;

@Data
public class OrderItem {
    private int productId;
    private String productName;
    private int quantity;
    private double price;
}