package com.whitbread.demo.service;

import com.whitbread.demo.model.Hotel;
import com.whitbread.demo.model.HotelList;
import com.whitbread.demo.service.impl.HotelServiceImpl;
import com.whitbread.demo.util.HotelTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;

import static  org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class HotelServiceTest {

    @Autowired
    HotelServiceImpl service;

    @Before
    public void setUp(){
        HotelList list = HotelTestHelper.createHotelList(5);
        HotelList monoList = HotelTestHelper.createHotelList(1);

        Hotel monoHotel =HotelTestHelper.addHotelToList(monoList,"ABEAIB", "Aberdeen Airport hotel");

        Hotel h1= HotelTestHelper.addHotelToList(list,"ABEAIB", "Our Aberdeen Airport hotel is the ideal stop-off whether you're jetting away or in the area on business. We're handy for the A96 and all roads leading north, while all the sights and sounds of Aberdeen are close at hand too. The hotel features our new generation bedrooms, each with an impressive 40\\\" flat screen TV, freshly-updated bathroom with large shower head, and brand new, slumber-inducing, king-size Hypnos bed. \\n\\nSee your favourite rock band at the AECC, the North East of Scotland's premier event venue. Or stroll through the Aberdeen, enjoying the sparkling silver buildings of the so-called Granite City");
        Hotel h2= HotelTestHelper.addHotelToList(list,"BANWOB", "Banbury - Closed");
        Hotel h3= HotelTestHelper.addHotelToList(list,"BATJAM", "Bath City Centre");
        Hotel h4= HotelTestHelper.addHotelToList(list,"COVLOC", "Coventry South (A45)");
        Hotel h5= HotelTestHelper.addHotelToList(list,"LIVSHI", "Liverpool Rainhill");

        HotelTestHelper.addFacitilityToList(monoHotel, "LFT", "Lift Access");
        HotelTestHelper.addFacitilityToList(monoHotel, "CPF", "Free Car Parking");

        HotelTestHelper.addFacitilityToList(h1, "LFT", "Lift Access");
        HotelTestHelper.addFacitilityToList(h1, "CPP", "Free Car Parking");
        HotelTestHelper.addFacitilityToList(h1, "BLC", "Balcony");

        HotelTestHelper.addFacitilityToList(h2, "ENS", "En-suite");
        HotelTestHelper.addFacitilityToList(h2, "LFT", "Lift Access");
        HotelTestHelper.addFacitilityToList(h2, "CPF", "Free Car Parking");
        HotelTestHelper.addFacitilityToList(h2, "BLC", "Balcony");

        HotelTestHelper.addFacitilityToList(h3, "ENS", "En-suite");
        HotelTestHelper.addFacitilityToList(h3, "CPP", "Paid Car Parking");
        HotelTestHelper.addFacitilityToList(h3, "BLC", "Balcony");

        HotelTestHelper.addFacitilityToList(h4, "ENS", "En-suite");
        HotelTestHelper.addFacitilityToList(h4, "CPP", "Free Car Parking");

        HotelTestHelper.addFacitilityToList(h5, "CPP", "Paid Car Parking");
        HotelTestHelper.addFacitilityToList(h5, "LFT", "Lift Access");

        HotelTestHelper.addRatingToHotel(h1, 3.3);
        HotelTestHelper.addRatingToHotel(h2, 3.1);
        HotelTestHelper.addRatingToHotel(h3, 4.0);
        HotelTestHelper.addRatingToHotel(h4, 4.6);
        HotelTestHelper.addRatingToHotel(h5, 3.3);
        final var mock = Mockito.mock(WebClient.class);
        final var uriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        final var headersSpecMock1 = Mockito.mock(WebClient.RequestHeadersSpec.class);
        final var responseSpecMock1 = Mockito.mock(WebClient.ResponseSpec.class);
        final var headersSpecMock2 = Mockito.mock(WebClient.RequestHeadersSpec.class);
        final var responseSpecMock2 = Mockito.mock(WebClient.ResponseSpec.class);
        final var headersSpecMock3 = Mockito.mock(WebClient.RequestHeadersSpec.class);
        final var responseSpecMock3 = Mockito.mock(WebClient.ResponseSpec.class);

        service= new HotelServiceImpl(mock);
        when(mock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(ArgumentMatchers.endsWith("?hotelCodes=ABEAIB"))).thenReturn(headersSpecMock1);
        when(headersSpecMock1.header(notNull(), notNull())).thenReturn(headersSpecMock1);
        when(headersSpecMock1.headers(notNull())).thenReturn(headersSpecMock1);
        when(headersSpecMock1.retrieve()).thenReturn(responseSpecMock1);
        when(responseSpecMock1.bodyToMono(HotelList.class))
                .thenReturn(Mono.just(monoList));
        when(uriSpecMock.uri(ArgumentMatchers.endsWith("?hotelCodes=ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"))).thenReturn(headersSpecMock2);
        when(headersSpecMock2.header(notNull(), notNull())).thenReturn(headersSpecMock2);
        when(headersSpecMock2.headers(notNull())).thenReturn(headersSpecMock2);
        when(headersSpecMock2.retrieve()).thenReturn(responseSpecMock2);
        when(responseSpecMock2.bodyToMono(HotelList.class))
                .thenReturn(Mono.just(list));

        when(uriSpecMock.uri(ArgumentMatchers.endsWith("?hotelCodes=PTR,LMN,ABC,XYZ"))).thenReturn(headersSpecMock3);
        when(uriSpecMock.uri(ArgumentMatchers.endsWith("?hotelCodes=ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI,XYZ"))).thenReturn(headersSpecMock2);
        when(uriSpecMock.uri(ArgumentMatchers.endsWith("?hotelCodes=XYZ"))).thenReturn(headersSpecMock3);
        when(headersSpecMock3.header(notNull(), notNull())).thenReturn(headersSpecMock3);
        when(headersSpecMock3.headers(notNull())).thenReturn(headersSpecMock3);
        when(headersSpecMock3.retrieve()).thenReturn(responseSpecMock3);
        when(responseSpecMock3.bodyToMono(HotelList.class))
                .thenReturn(Mono.just(HotelTestHelper.createHotelList(0)));
    }

    @Test
    public void getSingleHotelDetailsBasedOnValidHotelCode(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB"), null, null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 1);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 1);
    }
    @Test
    public void getMultipleHotelDetailsBasedOnValidHotelCodes(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"), null, null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 5);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 5);
    }
    @Test
    public void getSingleHotelDetailsForAInvalidHotelCode(){
        HotelList result = service.getHotelDetails(Arrays.asList("XYZ"), null, null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 0);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 0);
    }
    @Test
    public void getMultipleHotelDetailsWithAInvalidHotelCode(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI,XYZ"), null, null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 5);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 5);
    }
    @Test
    public void getMultipleHotelDetailsWithAllInvalidHotelCodes(){
        HotelList result = service.getHotelDetails(Arrays.asList("PTR,LMN,ABC,XYZ"), null, null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 0);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 0);
    }
    @Test
    public void getHotelDetailsForNullValueAsHotelCodes(){
        HotelList result = service.getHotelDetails(null, null, null);
        assertNull(result);
    }
    @Test
    public void getHotelDetailsForEmptyListAsHotelCodes(){
        HotelList result = service.getHotelDetails(new ArrayList<>(), null, null);
        assertNull(result);
    }

    @Test
    public void getSingleHotelDetailsBasedOnValidHotelCodeAndSingleFilterMatchingFacility(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB"), Arrays.asList("LFT"), null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 1);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 1);
    }
    @Test
    public void getSingleHotelDetailsBasedOnValidHotelCodeAndMultipleFilterMatchingFacility(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB"), Arrays.asList("LFT","CPF"), null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 1);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 1);
    }
    @Test
    public void getSingleHotelDetailsBasedOnValidHotelCodeAndSingleFilterNotMatchingFacility(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB"), Arrays.asList("BLC"), null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 0);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 0);
    }
    @Test
    public void getSingleHotelDetailsBasedOnValidHotelCodeAndMultipleFilterNotMatchingFacility(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB"), Arrays.asList("LFT","BLC"), null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 0);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 0);
    }
    @Test
    public void getMultipleHotelDetailsBasedOnValidHotelCodesAndSingleFilterMatchingFacility(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"), Arrays.asList("LFT"), null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 3);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 3);
    }

    @Test
    public void getMultipleHotelDetailsBasedOnValidHotelCodesAndMultipleFilterMatchingFacility(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"), Arrays.asList("LFT", "BLC"), null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 2);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 2);
    }
    @Test
    public void getMultipleHotelDetailsBasedOnValidHotelCodesAndSingleFilterNonMatchingFacility(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"), Arrays.asList("XYZ"), null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 0);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 0);
    }
    @Test
    public void getMultipleHotelDetailsBasedOnValidHotelCodesAndMultipleFilterNonMatchingFacility(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"), Arrays.asList("LFT", "XYZ"), null);
        assertNotNull(result);
        assertEquals(result.getTotal(), 0);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 0);
    }

    @Test
    public void getMultipleHotelDetailsBasedOnValidHotelCodesAndSingleFilterMatchingFacilityAndSortingAsc(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"), Arrays.asList("LFT"), "ASC");
        assertNotNull(result);
        assertEquals(result.getTotal(), 3);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 3);
        assertEquals(result.getHotels().get(0).getCode(), "BANWOB");
    }
    @Test
    public void getMultipleHotelDetailsBasedOnValidHotelCodesAndMultipleFilterMatchingFacilityAndSortingDesc(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"), Arrays.asList("LFT", "BLC"), "DESC");
        assertNotNull(result);
        assertEquals(result.getTotal(), 2);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 2);
        assertEquals(result.getHotels().get(0).getCode(), "ABEAIB");
    }

    @Test
    public void getMultipleHotelDetailsBasedOnValidHotelCodesAndMultipleFilterMatchingFacilityAndSortingEmpty(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"), Arrays.asList("LFT", "BLC"), "");
        assertNotNull(result);
        assertEquals(result.getTotal(), 2);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 2);
    }
    @Test
    public void getMultipleHotelDetailsBasedOnValidHotelCodesAndSingleFilterMatchingFacilityAndSortingDescWithSameRating(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"), Arrays.asList("LFT"), "DESC");
        assertNotNull(result);
        assertEquals(result.getTotal(), 3);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 3);
        assertEquals(result.getHotels().get(0).getCode(), "ABEAIB");
    }
    @Test
    public void getMultipleHotelDetailsBasedOnValidHotelCodesAndMultipleFilterMatchingFacilityAndSortingAscWithSameRating(){
        HotelList result = service.getHotelDetails(Arrays.asList("ABEAIB,BANWOB,BATJAM,COVLOC,LIVSHI"), Arrays.asList("LFT", "CPP"), "ASC");
        assertNotNull(result);
        assertEquals(result.getTotal(), 2);
        assertNotNull(result.getHotels());
        assertEquals(result.getHotels().size(), 2);
        assertEquals(result.getHotels().get(0).getCode(), "ABEAIB");
    }
}
