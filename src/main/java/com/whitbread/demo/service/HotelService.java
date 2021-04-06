package com.whitbread.demo.service;

import com.whitbread.demo.model.HotelList;

import java.util.List;


public interface HotelService {
    HotelList getHotelDetails(List<String> hotelCodes, List<String> facilityCodes, String sortByTripAdvisor);

}
