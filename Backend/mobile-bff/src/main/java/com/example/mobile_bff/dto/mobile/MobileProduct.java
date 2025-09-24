package com.example.mobilebff.dto.mobile;

import lombok.Data;

@Data
public class MobileProduct {
    private int id;
    private String name;
    private double price;
    private String image;
    private String description;
    private double avgRating;
}