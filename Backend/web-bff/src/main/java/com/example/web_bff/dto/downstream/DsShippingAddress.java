package com.example.webbff.dto.downstream;

import lombok.Data;

@Data
public class DsShippingAddress {
    private String street;
    private String city;
    private String state;
    private String zipCode;
}