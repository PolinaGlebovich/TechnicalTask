package com.example.technicaltask.controller;

import com.example.technicaltask.dto.HotelAllDataDto;
import com.example.technicaltask.dto.HotelCreationDto;
import com.example.technicaltask.dto.HotelGeneralDto;
import com.example.technicaltask.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("property-view")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/hotels")
    public List<HotelGeneralDto> findAllHotels() {
        return hotelService.findAll();
    }

    @GetMapping("/hotels/{id}")
    public Optional<HotelAllDataDto> findHotelById(@PathVariable Long id) {
        return hotelService.findById(id);
    }

    @GetMapping("/search")
    public List<HotelGeneralDto> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities
    ) {
        return hotelService.searchByParameters(name, brand, city, country, amenities);
    }

    @PostMapping("/hotels")
    public HotelGeneralDto save(@Valid @RequestBody HotelCreationDto hotelCreationDto) {
        return hotelService.save(hotelCreationDto);
    }

    @PostMapping("/{id}/amenities")
    public ResponseEntity<Void> addAmenities(
            @PathVariable Long id,
            @RequestBody Set<String> amenities
    ) {
        boolean updated = hotelService.addAmenitiesToHotel(id, amenities);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}
