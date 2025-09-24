package com.example.productservice.model;

import lombok.Data;

@Data
public class Review {
    private String user;
    private int rating;
    private String text;
    private String date;
}
