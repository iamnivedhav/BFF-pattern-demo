package com.example.mobilebff.dto.downstream;

import lombok.Data;

@Data
public class DsReview {
    private String user;
    private int rating;
    private String text;
    private String date;
}