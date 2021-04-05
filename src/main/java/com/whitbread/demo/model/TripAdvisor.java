package com.whitbread.demo.model;

import lombok.Data;

@Data
public class TripAdvisor {
    private double rateUpperRange;
    private double rating;
    private String linkUrl;
    private String hotelId;
    private String ratingImageUrl;
    private String tripAdvisorId;
    private int sampleSize;
    private double rateLowerRange;
    private double stepSize;
    private String altText;
}
