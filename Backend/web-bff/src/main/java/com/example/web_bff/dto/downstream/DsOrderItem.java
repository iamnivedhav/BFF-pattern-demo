package com.example.webbff.dto.downstream;

import lombok.Data;

@Data
public class DsOrderItem {
    private int productId;
    private String productName;
    private int quantity;
    private double price;
}