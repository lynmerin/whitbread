package com.whitbread.demo.model;

import lombok.Data;

@Data
public class RestaurantTime {
    private WeekdayValues weekdayValues;
    private WeekendValues weekendValues;
}
