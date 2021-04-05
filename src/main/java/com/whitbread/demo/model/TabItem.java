package com.whitbread.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class TabItem {
    private List<String> renditions;
    private String roomType;
    private String roomTitle;
    private String roomDescriptionText;
    private List<NewFacility> newFacilities;
    private String readMoreLabel;
    private String roomInfoLabel;
    private String roomInfoText;
    private String room;
}
