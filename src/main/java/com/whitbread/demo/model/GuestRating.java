package com.whitbread.demo.model;

import lombok.Data;

@Data
public class GuestRating {
    private double rateUpperRange;
    private double rating;
    private double sampleSize;
    private double rateLowerRange;
    private double stepSize;
    private double likelihoodToRecommend;
}
