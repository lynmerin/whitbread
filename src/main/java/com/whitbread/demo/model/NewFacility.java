package com.whitbread.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class NewFacility {
    private String facilitiesTitle;
    private List<NavOption> navOptions;
}
