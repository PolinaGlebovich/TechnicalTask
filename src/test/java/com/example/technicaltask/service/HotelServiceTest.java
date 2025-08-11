package com.example.technicaltask.service;

import com.example.technicaltask.dto.*;
import com.example.technicaltask.entity.Hotel;
import com.example.technicaltask.mapper.HotelAllDataMapper;
import com.example.technicaltask.mapper.HotelCreationMapper;
import com.example.technicaltask.mapper.HotelGeneralMapper;
import com.example.technicaltask.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelCreationMapper hotelCreationMapper;

    @Mock
    private HotelGeneralMapper hotelGeneralMapper;

    @Mock
    private HotelAllDataMapper hotelAllDataMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    void findAll_ShouldReturnMappedHotels() {
        Hotel hotel = new Hotel();
        HotelGeneralDto dto =new HotelGeneralDto(1L, "Test Hotel", "BrandX", "CityY", "CountryZ");
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));
        when(hotelGeneralMapper.toDto(hotel)).thenReturn(dto);

        List<HotelGeneralDto> result = hotelService.findAll();

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
        verify(hotelRepository).findAll();
        verify(hotelGeneralMapper).toDto(hotel);
    }

    @Test
    void findById_ShouldReturnMappedDto_WhenHotelExists() {
        Hotel hotel = new Hotel();
        HotelAllDataDto dto = new HotelAllDataDto(1L, "Test Hotel", "BrandX", "CityY",
                new AddressDto(2, "Test", "Test", "Test", "Test"),
                new ContactsDto("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeDto(LocalTime.of(15,0), LocalTime.of(16,0)),  Set.of("WiFi"));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelAllDataMapper.toDto(hotel)).thenReturn(dto);

        Optional<HotelAllDataDto> result = hotelService.findById(1L);

        assertTrue(result.isPresent());
        assertSame(dto, result.get());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenHotelDoesNotExist() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<HotelAllDataDto> result = hotelService.findById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void save_ShouldMapAndSaveHotel() {
        HotelCreationDto creationDto =new HotelCreationDto( "Test Hotel", "BrandX", "CityY",
                new AddressDto(3, "Test", "Test", "Test", "Test"),
                new ContactsDto("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeDto(LocalTime.of(15,0), LocalTime.of(16,0)));

        Hotel hotel = new Hotel();
        Hotel savedHotel = new Hotel();
        HotelGeneralDto dto = new HotelGeneralDto(1L, "Test", "Test", "Test", "+375 17 309-80-00");

        when(hotelCreationMapper.toEntity(creationDto)).thenReturn(hotel);
        when(hotelRepository.save(hotel)).thenReturn(savedHotel);
        when(hotelGeneralMapper.toDto(savedHotel)).thenReturn(dto);

        HotelGeneralDto result = hotelService.save(creationDto);

        assertSame(dto, result);
        verify(hotelCreationMapper).toEntity(creationDto);
        verify(hotelRepository).save(hotel);
    }

    @Test
    void addAmenitiesToHotel_ShouldReturnFalse_WhenHotelNotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = hotelService.addAmenitiesToHotel(1L, Set.of("Pool"));

        assertFalse(result);
    }

    @Test
    void addAmenitiesToHotel_ShouldAddAmenitiesAndReturnTrue() {
        Hotel hotel = new Hotel();
        hotel.setAmenities(new HashSet<>());
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        boolean result = hotelService.addAmenitiesToHotel(1L, Set.of("Pool"));

        assertTrue(result);
        assertTrue(hotel.getAmenities().contains("Pool"));
        verify(hotelRepository).save(hotel);
    }

    @Test
    void getHistogram_ShouldReturnCorrectMap_ForBrand() {
        List<Map<String, Object>> mockData = List.of(
                Map.of("key", "BrandA", "count", 3),
                Map.of("key", "BrandB", "count", 5)
        );
        when(hotelRepository.countGroupByBrand()).thenReturn(mockData);

        Map<String, Long> histogram = hotelService.getHistogram("brand");

        assertEquals(2, histogram.size());
        assertEquals(3L, histogram.get("BrandA"));
        assertEquals(5L, histogram.get("BrandB"));
    }

    @Test
    void getHistogram_ShouldThrowException_WhenUnknownParam() {
        assertThrows(IllegalArgumentException.class, () -> hotelService.getHistogram("unknown"));
    }
}
