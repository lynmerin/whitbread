package com.whitbread.demo.service;

import com.whitbread.demo.model.HotelList;
import org.springframework.stereotype.Service;

import java.util.List;


public interface HotelService {
    HotelList getHotelDetails(List<String> hotelCodes, List<String> facilityCodes, String sortByTripAdvisor);

}
