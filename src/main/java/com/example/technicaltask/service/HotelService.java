package com.example.technicaltask.service;

import com.example.technicaltask.dto.HotelAllDataDto;
import com.example.technicaltask.dto.HotelCreationDto;
import com.example.technicaltask.dto.HotelGeneralDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface HotelService {
    List<HotelGeneralDto> findAll();
    Optional<HotelAllDataDto> findById(Long id);
    List<HotelGeneralDto> searchByParameters(String name, String brand, String city, String country, String amenity);
    HotelGeneralDto save (HotelCreationDto hotelCreationDto);
    public boolean addAmenitiesToHotel(Long hotelId, Set<String> amenities);
    Map<String, Long> getHistogram(String param);
}