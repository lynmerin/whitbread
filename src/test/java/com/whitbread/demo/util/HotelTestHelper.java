package com.whitbread.demo.util;

import com.whitbread.demo.model.Facility;
import com.whitbread.demo.model.Hotel;
import com.whitbread.demo.model.HotelList;
import com.whitbread.demo.model.TripAdvisor;

import java.util.ArrayList;

public class HotelTestHelper {

    public static HotelList createHotelList(int total){
        HotelList list = new HotelList();
        list.setTotal(total);
        list.setHotels(new ArrayList<>());
        return list;
    }

    public static Hotel addHotelToList(HotelList list, String code, String desc){
        Hotel hotel = new Hotel();
        hotel.setCode(code);
        hotel.setHotelDescription(desc);
        hotel.setFacilities(new ArrayList<>());
        list.getHotels().add(hotel);
        return hotel;
    }

    public static  Facility addFacitilityToList(Hotel hotel, String code , String legend){
        Facility facility = new Facility();
        facility.setCode(code);
        facility.setLegend(legend);
        hotel.getFacilities().add(facility);
        return facility;
    }

    public static void addRatingToHotel(Hotel hotel, double rating){
        TripAdvisor ta = new TripAdvisor();
        ta.setRating(rating);
        hotel.setTripAdvisor(ta);
    }
}
