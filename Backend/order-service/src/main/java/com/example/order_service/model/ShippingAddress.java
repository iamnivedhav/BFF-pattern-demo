package com.example.orderservice.model;

import lombok.Data;

@Data
public class ShippingAddress {
    private String street;
    private String city;
    private String state;
    private String zipCode;
}