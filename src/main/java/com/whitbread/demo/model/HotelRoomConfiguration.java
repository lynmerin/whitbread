package com.whitbread.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class HotelRoomConfiguration {
    private String title;
    private List<TabItem> tabItems;
}
