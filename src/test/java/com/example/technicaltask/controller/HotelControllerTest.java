package com.example.technicaltask.controller;

import com.example.technicaltask.dto.*;
import com.example.technicaltask.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HotelService hotelService;

    private HotelGeneralDto generalDto;
    private HotelAllDataDto allDataDto;

    @BeforeEach
    void setUp() {
        generalDto = new HotelGeneralDto(1L, "Test Hotel", "BrandX", "CityY", "CountryZ");
        allDataDto = new HotelAllDataDto(1L, "Test Hotel", "BrandX", "CityY",
                new AddressDto(3, "Test", "Test", "Test", "Test"),
                new ContactsDto("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeDto(LocalTime.of(15,0), LocalTime.of(16,0)),  Set.of("WiFi"));
    }

    @Test
    void findAllHotels_ShouldReturnList() throws Exception {
        Mockito.when(hotelService.findAll()).thenReturn(List.of(generalDto));

        mockMvc.perform(get("/property-view/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Hotel"));
    }

    @Test
    void findHotelById_ShouldReturnHotel() throws Exception {
        Mockito.when(hotelService.findById(1L)).thenReturn(Optional.of(allDataDto));

        mockMvc.perform(get("/property-view/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Hotel"));
    }

    @Test
    void searchHotels_ShouldReturnFilteredList() throws Exception {
        Mockito.when(hotelService.searchByParameters("Test Hotel", null, null, null, null))
                .thenReturn(List.of(generalDto));

        mockMvc.perform(get("/property-view/search")
                        .param("name", "Test Hotel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Hotel"));
    }

    @Test
    void save_ShouldCreateHotel() throws Exception {
        HotelCreationDto creationDto = new HotelCreationDto( "Test Hotel", "BrandX", "CityY",
                new AddressDto(3, "Test", "Test", "Test", "Test"),
                new ContactsDto("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeDto(LocalTime.of(15,0), LocalTime.of(16,0)));
        Mockito.when(hotelService.save(any(HotelCreationDto.class))).thenReturn(generalDto);

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Hotel"));
    }

    @Test
    void addAmenities_ShouldReturnOk_WhenUpdated() throws Exception {
        Mockito.when(hotelService.addAmenitiesToHotel(eq(1L), any(Set.class))).thenReturn(true);

        mockMvc.perform(post("/property-view/hotels/1/amenities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Set.of("Pool"))))
                .andExpect(status().isOk());
    }

    @Test
    void addAmenities_ShouldReturnNotFound_WhenNotUpdated() throws Exception {
        Mockito.when(hotelService.addAmenitiesToHotel(eq(1L), any(Set.class))).thenReturn(false);

        mockMvc.perform(post("/property-view/1/amenities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Set.of("Pool"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void getHistogram_ShouldReturnMap() throws Exception {
        Map<String, Long> histogram = Map.of("BrandX", 5L);
        Mockito.when(hotelService.getHistogram("brand")).thenReturn(histogram);

        mockMvc.perform(get("/property-view/histogram/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.BrandX").value(5));
    }
}
