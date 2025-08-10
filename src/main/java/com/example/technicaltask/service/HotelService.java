package com.example.technicaltask.service;

import com.example.technicaltask.entity.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    List<Hotel> findAll();
    Optional<Hotel> findById(Long id);
    List<Hotel> searchByParameters(String param);
}
