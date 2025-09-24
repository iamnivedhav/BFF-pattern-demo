package com.example.mobilebff.dto.mobile;

import lombok.Data;

@Data
public class MobileOrderSummary {
    private String id;
    private String date;
    private String status;
    private double totalAmount;
}