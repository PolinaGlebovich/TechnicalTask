package com.example.technicaltask.service;

import com.example.technicaltask.dto.HotelAllDataDto;
import com.example.technicaltask.dto.HotelCreationDto;
import com.example.technicaltask.dto.HotelGeneralDto;
import com.example.technicaltask.entity.Hotel;
import com.example.technicaltask.mapper.HotelAllDataMapper;
import com.example.technicaltask.mapper.HotelCreationMapper;
import com.example.technicaltask.mapper.HotelGeneralMapper;
import com.example.technicaltask.repository.HotelRepository;
import com.example.technicaltask.repository.specification.HotelSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelCreationMapper hotelCreationMapper;
    private final HotelGeneralMapper hotelGeneralMapper;
    private final HotelAllDataMapper hotelAllDataMapper;

    @Override
    public List<HotelGeneralDto> findAll() {
        return hotelRepository.findAll().stream()
                .map(hotelGeneralMapper::toDto)
                .toList();
    }

    @Override
    public Optional<HotelAllDataDto> findById(Long id) {
        return hotelRepository.findById(id)
                .map(hotelAllDataMapper::toDto);
    }

    @Override
    public List<HotelGeneralDto> searchByParameters(String name, String brand, String city, String country, String amenity) {
        Specification<Hotel> spec = Specification.allOf(
                HotelSpecification.hasName(name),
                HotelSpecification.hasBrand(brand),
                HotelSpecification.hasCity(city),
                HotelSpecification.hasCountry(country),
                HotelSpecification.hasAmenity(amenity)
        );
        return hotelRepository.findAll(spec).stream()
                .map(hotelGeneralMapper::toDto)
                .toList();
    }

    @Override
    public HotelGeneralDto save(HotelCreationDto hotelCreationDto) {
        Hotel hotel = hotelCreationMapper.toEntity(hotelCreationDto);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelGeneralMapper.toDto(savedHotel);
    }

    @Override
    public boolean addAmenitiesToHotel(Long hotelId, Set<String> amenities) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);
        if (hotelOpt.isEmpty()) {
            return false;
        }

        Hotel hotel = hotelOpt.get();
        if (hotel.getAmenities() == null) {
            hotel.setAmenities(new HashSet<>());
        }
        hotel.getAmenities().addAll(amenities);

        hotelRepository.save(hotel);
        return true;
    }

    @Override
    public Map<String, Long> getHistogram(String param) {
        List<Map<String, Object>> data = switch (param.toLowerCase()) {
            case "brand" -> hotelRepository.countGroupByBrand();
            case "city" -> hotelRepository.countGroupByCity();
            case "country" -> hotelRepository.countGroupByCountry();
            case "amenities" -> hotelRepository.countGroupByAmenity();
            default -> throw new IllegalArgumentException("Unknown histogram parameter: " + param);
        };
        return data.stream().collect(Collectors.toMap(
                row -> String.valueOf(row.get("key")),
                row -> ((Number) row.get("count")).longValue()
        ));
    }
}
