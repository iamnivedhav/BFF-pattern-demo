package com.example.webbff.dto.web;

import com.example.webbff.dto.downstream.DsReview;
import lombok.Data;
import java.util.List;

// This is the rich model for the Web Frontend
@Data
public class WebProduct {
    private int id;
    private String name;
    private String brand;
    private double price;
    private String shortDescription;
    private String image;
    private double avgRating;
    private int totalReviews; // A new, calculated field
    private int stock;
    private String fullDescription;
    private List<DsReview> reviews;
}