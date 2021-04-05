package com.whitbread.demo.model;

import lombok.Data;

@Data
public class Announcement {
    private String title;
    private String announcementSource;
    private boolean showAnnouncement;
    private String startDate;
    private String endDate;
    private String fullBannerLink;
    private boolean fullBannerLinkNewTab;
    private String icon;
    private String text;
    private String type;
}
