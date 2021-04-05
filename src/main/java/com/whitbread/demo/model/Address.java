package com.whitbread.demo.model;

import lombok.Data;

@Data
public class Address {
    private String addressline1;
    private String addressline2;
    private String addressline3;
    private String country;
    private String postcode;
}
