package com.whitbread.demo.model;

import lombok.Data;

import java.util.List;
@Data
public class Restaurant {
    private String brand;
    private String name;
    private String description;
    private String image;
    private List<Menu> menus;
}
