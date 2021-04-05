package com.whitbread.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class Hotel {
    private ContactDetails contactDetails;
    private String hotelDescription;
    private String hotelOpeningDate;
    private String hotelSPFDescription;
    private String hotelDirections;
    private Location location;
    private List<AcceptedCreditCard> acceptedCreditCards;
    private String parkingDescription;
    private String hotelGooglePlus;
    private String parkName;
    private String img;
    private List<Object> leftList;
    private List<Object> rightList;
    private String distanceOverride;
    private boolean usingNewBookingFlow;
    private String code;
    private String name;
    private String headline;
    private String brand;
    private Address address;
    private Map map;
    private List<Image> images;
    private List<Facility> facilities;
    private GuestRating guestRating;
    private TripAdvisor tripAdvisor;
    private MessagingFlag messagingFlag;
    private Links links;
    private HotelRoomConfiguration hotelRoomConfiguration;
    private CityTax cityTax;
    private Restaurant restaurant;
    private RestaurantTime restaurantTime;
    private ThreeWords threeWords;
    private Announcement announcement;
    private boolean authenticationRequired;


}
