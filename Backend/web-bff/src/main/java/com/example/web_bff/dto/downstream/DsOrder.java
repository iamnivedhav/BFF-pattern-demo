package com.example.webbff.dto.downstream;

import lombok.Data;
import java.util.List;

@Data
public class DsOrder {
    private String id;
    private String customerId;
    private String date;
    private String status;
    private List<DsOrderItem> items;
    private DsShippingAddress shippingAddress;
    private double totalAmount;
}