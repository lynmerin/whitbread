package com.whitbread.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class Image {
    private List<String> tags;
    private String altText;
    private List<String> renditions;
    private String fileReference;
}
