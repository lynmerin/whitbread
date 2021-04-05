package com.whitbread.demo.controller;

import com.whitbread.demo.model.HotelList;
import com.whitbread.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotelDetails")
public class HotelController {

    @Autowired
    HotelService service;

    @GetMapping
    public HotelList getHotelDetails(@RequestParam(value = "hotelCodes", required = true) final List<String> hotelCodes, @RequestParam(value= "facilityCodes", required = false) final List<String> facilityCodes, @RequestParam(value="sortByTripAdvisor", required = false) final String sortByTripAdvisor ){
        return service.getHotelDetails(hotelCodes, facilityCodes,sortByTripAdvisor);
    }
}
