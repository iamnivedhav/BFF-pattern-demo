package com.example.mobilebff.dto.downstream;

import lombok.Data;
import java.util.List;

@Data
public class DsProduct {
    private int id;
    private String name;
    private String brand;
    private double price;
    private String description;
    private String shortDesc;
    private String image;
    private int stock;
    private double rating;
    private int ratingCount;
    private int soldLastWeek;
    private int soldLastMonth;
    private List<DsReview> reviews;
}