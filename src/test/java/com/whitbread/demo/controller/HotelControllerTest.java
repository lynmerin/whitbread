package com.whitbread.demo.controller;

import com.whitbread.demo.model.Hotel;
import com.whitbread.demo.model.HotelList;
import com.whitbread.demo.util.HotelTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.Arrays;
import static  org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@WebFluxTest(controllers = HotelController.class)
public class HotelControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private HotelService service;

    @Test
    public void getSingleHotelValidCode() throws Exception {
        HotelList list = HotelTestHelper.createHotelList(1);
        HotelTestHelper.addHotelToList(list,"ABEAIB", "Our Aberdeen Airport hotel is the ideal stop-off whether you're jetting away or in the area on business. We're handy for the A96 and all roads leading north, while all the sights and sounds of Aberdeen are close at hand too. The hotel features our new generation bedrooms, each with an impressive 40\\\" flat screen TV, freshly-updated bathroom with large shower head, and brand new, slumber-inducing, king-size Hypnos bed. \\n\\nSee your favourite rock band at the AECC, the North East of Scotland's premier event venue. Or stroll through the Aberdeen, enjoying the sparkling silver buildings of the so-called Granite City");
        when(service.getHotelDetails(Arrays.asList("ABEAIB"), null, null)).thenReturn(list);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("hotelCodes", "ABEAIB");

        HotelList result = webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/hotelDetails")
                                .queryParams(map)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HotelList.class).returnResult().getResponseBody();
        assertNotNull(result);
        assertEquals(result.getTotal(), 1);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 1);
        assertEquals(result.getHotels().get(0).getCode(), "ABEAIB" );

    }

    @Test
    public void getMultipleHotelValidCode() throws Exception {
        HotelList list = HotelTestHelper.createHotelList(2);
        HotelTestHelper.addHotelToList(list,"ABEAIB", "Our Aberdeen Airport hotel is the ideal stop-off whether you're jetting away or in the area on business. We're handy for the A96 and all roads leading north, while all the sights and sounds of Aberdeen are close at hand too. The hotel features our new generation bedrooms, each with an impressive 40\\\" flat screen TV, freshly-updated bathroom with large shower head, and brand new, slumber-inducing, king-size Hypnos bed. \\n\\nSee your favourite rock band at the AECC, the North East of Scotland's premier event venue. Or stroll through the Aberdeen, enjoying the sparkling silver buildings of the so-called Granite City");
        HotelTestHelper.addHotelToList(list,"BANWOB", "Premier Inn Banbury is set in beautiful countryside on the edge of the Cotswolds, with an amazing range of family attractions close by. \\n\\nChoose between William Shakespeare's Stratford-upon-Avon or the Heritage Motor Centre and Museum");
        when(service.getHotelDetails(Arrays.asList("ABEAIB", "BANWOB"), null, null)).thenReturn(list);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("hotelCodes", "ABEAIB,BANWOB");

        HotelList result = webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/hotelDetails")
                                .queryParams(map)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HotelList.class).returnResult().getResponseBody();
        assertNotNull(result);
        assertEquals(result.getTotal(), 2);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 2);

    }

    @Test
    public void getSingleHotelInvalidCode() throws Exception {
        HotelList list = HotelTestHelper.createHotelList(0);
        when(service.getHotelDetails(Arrays.asList("XYZ"), null, null)).thenReturn(list);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("hotelCodes", "XYZ");

        HotelList result = webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/hotelDetails")
                                .queryParams(map)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HotelList.class).returnResult().getResponseBody();
        assertNotNull(result);
        assertEquals(result.getTotal(), 0);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 0);

    }
    @Test
    public void getMultipleHotelWithInvalidCode() throws Exception {
        HotelList list = HotelTestHelper.createHotelList(2);
        HotelTestHelper.addHotelToList(list,"ABEAIB", "Our Aberdeen Airport hotel is the ideal stop-off whether you're jetting away or in the area on business. We're handy for the A96 and all roads leading north, while all the sights and sounds of Aberdeen are close at hand too. The hotel features our new generation bedrooms, each with an impressive 40\\\" flat screen TV, freshly-updated bathroom with large shower head, and brand new, slumber-inducing, king-size Hypnos bed. \\n\\nSee your favourite rock band at the AECC, the North East of Scotland's premier event venue. Or stroll through the Aberdeen, enjoying the sparkling silver buildings of the so-called Granite City");
        HotelTestHelper.addHotelToList(list,"BANWOB", "Premier Inn Banbury is set in beautiful countryside on the edge of the Cotswolds, with an amazing range of family attractions close by. \\n\\nChoose between William Shakespeare's Stratford-upon-Avon or the Heritage Motor Centre and Museum");
        when(service.getHotelDetails(Arrays.asList("ABEAIB", "BANWOB", "XYZ"), null, null)).thenReturn(list);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("hotelCodes", "ABEAIB,BANWOB,XYZ");

        HotelList result = webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/hotelDetails")
                                .queryParams(map)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HotelList.class).returnResult().getResponseBody();
        assertNotNull(result);
        assertEquals(result.getTotal(), 2);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 2);

    }

    @Test
    public void getHotelWithoutPassingCode() throws Exception {
        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/hotelDetails")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void getSingleHotelWithMatchingFacilityCode() throws Exception {
        HotelList list = HotelTestHelper.createHotelList(1);
        Hotel hotel = HotelTestHelper.addHotelToList(list,"ABEAIB", "Our Aberdeen Airport hotel is the ideal stop-off whether you're jetting away or in the area on business. We're handy for the A96 and all roads leading north, while all the sights and sounds of Aberdeen are close at hand too. The hotel features our new generation bedrooms, each with an impressive 40\\\" flat screen TV, freshly-updated bathroom with large shower head, and brand new, slumber-inducing, king-size Hypnos bed. \\n\\nSee your favourite rock band at the AECC, the North East of Scotland's premier event venue. Or stroll through the Aberdeen, enjoying the sparkling silver buildings of the so-called Granite City");
        HotelTestHelper.addFacitilityToList(hotel, "LFT", "Lift Access");
        HotelTestHelper.addFacitilityToList(hotel, "CPF", "Free Parking");
        when(service.getHotelDetails(Arrays.asList("ABEAIB"), Arrays.asList("LFT"), null)).thenReturn(list);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("hotelCodes", "ABEAIB");
        map.add("facilityCodes", "LFT");
        HotelList result = webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/hotelDetails")
                                .queryParams(map)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HotelList.class).returnResult().getResponseBody();
        assertNotNull(result);
        assertEquals(result.getTotal(), 1);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 1);
        assertEquals(result.getHotels().get(0).getCode(), "ABEAIB" );

    }

    @Test
    public void getMultipleHotelWithMatchingFacility() throws Exception {
        HotelList list = HotelTestHelper.createHotelList(2);
        Hotel h1=  HotelTestHelper.addHotelToList(list,"ABEAIB", "Our Aberdeen Airport hotel is the ideal stop-off whether you're jetting away or in the area on business. We're handy for the A96 and all roads leading north, while all the sights and sounds of Aberdeen are close at hand too. The hotel features our new generation bedrooms, each with an impressive 40\\\" flat screen TV, freshly-updated bathroom with large shower head, and brand new, slumber-inducing, king-size Hypnos bed. \\n\\nSee your favourite rock band at the AECC, the North East of Scotland's premier event venue. Or stroll through the Aberdeen, enjoying the sparkling silver buildings of the so-called Granite City");
        Hotel h2= HotelTestHelper.addHotelToList(list,"BANWOB", "Premier Inn Banbury is set in beautiful countryside on the edge of the Cotswolds, with an amazing range of family attractions close by. \\n\\nChoose between William Shakespeare's Stratford-upon-Avon or the Heritage Motor Centre and Museum");
        HotelTestHelper.addFacitilityToList(h1, "LFT", "Lift Access");
        HotelTestHelper.addFacitilityToList(h1, "CPF", "Free parking");
        HotelTestHelper.addFacitilityToList(h2, "LFT", "Lift Access");
        when(service.getHotelDetails(Arrays.asList("ABEAIB", "BANWOB"), Arrays.asList("LFT"), null)).thenReturn(list);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("hotelCodes", "ABEAIB,BANWOB");
        map.add("facilityCodes", "LFT");
        HotelList result = webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/hotelDetails")
                                .queryParams(map)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HotelList.class).returnResult().getResponseBody();
        assertNotNull(result);
        assertEquals(result.getTotal(), 2);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 2);

    }

    @Test
    public void getMultipleHotelWithMatchingFacilitySortedAscending() throws Exception {
        HotelList list = HotelTestHelper.createHotelList(2);
        Hotel h1=  HotelTestHelper.addHotelToList(list,"ABEAIB", "Our Aberdeen Airport hotel is the ideal stop-off whether you're jetting away or in the area on business. We're handy for the A96 and all roads leading north, while all the sights and sounds of Aberdeen are close at hand too. The hotel features our new generation bedrooms, each with an impressive 40\\\" flat screen TV, freshly-updated bathroom with large shower head, and brand new, slumber-inducing, king-size Hypnos bed. \\n\\nSee your favourite rock band at the AECC, the North East of Scotland's premier event venue. Or stroll through the Aberdeen, enjoying the sparkling silver buildings of the so-called Granite City");
        Hotel h2= HotelTestHelper.addHotelToList(list,"BANWOB", "Premier Inn Banbury is set in beautiful countryside on the edge of the Cotswolds, with an amazing range of family attractions close by. \\n\\nChoose between William Shakespeare's Stratford-upon-Avon or the Heritage Motor Centre and Museum");
        HotelTestHelper.addFacitilityToList(h1, "LFT", "Lift Access");
        HotelTestHelper.addFacitilityToList(h1, "CPF", "Free parking");
        HotelTestHelper.addFacitilityToList(h2, "LFT", "Lift Access");
        when(service.getHotelDetails(Arrays.asList("ABEAIB", "BANWOB"), Arrays.asList("LFT"), "ASC")).thenReturn(list);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("hotelCodes", "ABEAIB,BANWOB");
        map.add("facilityCodes", "LFT");
        HotelList result = webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/hotelDetails")
                                .queryParams(map)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HotelList.class).returnResult().getResponseBody();
        assertNotNull(result);
        assertEquals(result.getTotal(), 2);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 2);

    }

}
