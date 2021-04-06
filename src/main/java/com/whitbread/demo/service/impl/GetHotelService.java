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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetHotelService implements HotelService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);

    private final WebClient loadHotelApi;

    @Autowired
    public GetHotelService(WebClient loadHotelApi) {
        this.loadHotelApi = loadHotelApi;
    }

    /**
     * Service method to call from the controller to get the hotel details based on filter and sorting.
     * @param hotelCodes
     * @param facilityCodes
     * @param sortByTripAdvisor
     * @return
     */
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

    /**
     * To load the list of hotels from an external API
     * @param hotelCodes
     * @return
     */
    private HotelList loadHotelExternalApi(List<String> hotelCodes) {
        String hotelCodesAsParam = hotelCodes.stream().map(String::toUpperCase).collect(Collectors.joining(","));
        return loadHotelApi.get().uri("?hotelCodes=" + hotelCodesAsParam).retrieve().bodyToMono(HotelList.class).block(REQUEST_TIMEOUT);
    }

    /**
     * To filter the hotel List based on list of facilities
     * @param facilityCodes
     * @param listToBeFiltered
     */
    private void filterHotelsWithFacility(List<String> facilityCodes, HotelList listToBeFiltered) {
        List<Hotel> hotelsFilteredList = listToBeFiltered.getHotels().stream().filter(hotel ->
                new HashSet<>(hotel.getFacilities().stream().map(Facility::getCode).collect(Collectors.toList())).containsAll(facilityCodes)
        ).collect(Collectors.toList());
        listToBeFiltered.setHotels(hotelsFilteredList);
        listToBeFiltered.setTotal(hotelsFilteredList.size());
    }

    /**
     * Sorting the Hotel list ASC or DESC based on the parameter passed
     * @param sortByTripAdvisor
     * @param listToBeSorted
     */
    private void sortHotelListBasedOnAdvisorRating(String sortByTripAdvisor, HotelList listToBeSorted) {
        if ("ASC".equalsIgnoreCase(sortByTripAdvisor))
            listToBeSorted.getHotels().sort(Comparator.comparingDouble((Hotel o) -> o.getTripAdvisor().getRating()).thenComparing(Comparator.comparing(Hotel::getCode)));
        else if ("DESC".equalsIgnoreCase(sortByTripAdvisor))
            listToBeSorted.getHotels().sort(Comparator.comparingDouble((Hotel o)  -> o.getTripAdvisor().getRating()).reversed().thenComparing(Comparator.comparing(Hotel::getCode)));

    }

}
