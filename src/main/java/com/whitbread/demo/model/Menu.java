package com.whitbread.demo.model;

import lombok.Data;

@Data
public class Menu {
    private String name;
    private String description;
    private String image;
    private String path;
    private String pathLabel;
    private String disclaimer;
}
