package com.whitbread.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class HotelList {
    private int total;
    private List<Hotel> hotels ;

}
