package com.whitbread.demo.service.impl;

import com.whitbread.demo.model.Facility;
import com.whitbread.demo.model.Hotel;
import com.whitbread.demo.model.HotelList;
import com.whitbread.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);

    private final WebClient loadHotelApi;

    @Autowired
    public HotelServiceImpl(WebClient loadHotelApi) {
        this.loadHotelApi = loadHotelApi;
    }

    @Override
    public HotelList getHotelDetails(List<String> hotelCodes, List<String> facilityCodes, String sortByTripAdvisor) {
        if (!CollectionUtils.isEmpty(hotelCodes)) {
            HotelList hotelList = loadHotelExternalApi(hotelCodes);
            if (!CollectionUtils.isEmpty(facilityCodes)) {
                filterHotelsWithFacility(facilityCodes, hotelList);
            }
            if (!StringUtils.isEmpty(sortByTripAdvisor)) {
                sortHotelListBasedOnAdvisorRating(sortByTripAdvisor, hotelList);
            }
            return hotelList;
        }
        return null;
    }

    private HotelList loadHotelExternalApi(List<String> hotelCodes) {
        String hotelCodesAsParam = hotelCodes.stream().map(String::toUpperCase).collect(Collectors.joining(","));
        return loadHotelApi.get().uri("?hotelCodes=" + hotelCodesAsParam).retrieve().bodyToMono(HotelList.class).block(REQUEST_TIMEOUT);
    }

    private void filterHotelsWithFacility(List<String> facilityCodes, HotelList listToBeFiltered) {
        List<Hotel> hotelsFilteredList = listToBeFiltered.getHotels().stream().filter(hotel ->
                hotel.getFacilities().stream().map(Facility::getCode).collect(Collectors.toList()).containsAll(facilityCodes)
        ).collect(Collectors.toList());
        listToBeFiltered.setHotels(hotelsFilteredList);
        listToBeFiltered.setTotal(hotelsFilteredList.size());
    }

    private void sortHotelListBasedOnAdvisorRating(String sortByTripAdvisor, HotelList listToBeSorted) {
        List<Hotel> sortedList = new ArrayList<>();
        if ("ASC".equalsIgnoreCase(sortByTripAdvisor))
            listToBeSorted.getHotels().sort(Comparator.comparingDouble((Hotel o) -> o.getTripAdvisor().getRating()).thenComparing(Comparator.comparing(Hotel::getCode)));
         else if ("DESC".equalsIgnoreCase(sortByTripAdvisor))
            listToBeSorted.getHotels().sort(Comparator.comparingDouble((Hotel o)  -> o.getTripAdvisor().getRating()).reversed().thenComparing(Comparator.comparing(Hotel::getCode)));

    }

}