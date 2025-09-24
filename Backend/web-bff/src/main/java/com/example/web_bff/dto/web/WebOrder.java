package com.example.webbff.dto.web;

import com.example.webbff.dto.downstream.DsOrderItem;
import com.example.webbff.dto.downstream.DsShippingAddress;
import lombok.Data;
import java.util.List;

@Data
public class WebOrder {
    private String id;
    private String customerId;
    private String date;
    private String status;
    private List<DsOrderItem> items;
    private DsShippingAddress shippingAddress;
    private double totalAmount;
}