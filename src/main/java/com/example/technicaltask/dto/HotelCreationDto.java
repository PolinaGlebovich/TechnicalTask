package com.example.technicaltask.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.Set;

@Data
public class HotelCreationDto {
    private String name;
    @Nullable
    private String description;
    private String brand;
    private AddressDto address;
    private ContactsDto contacts;
    private ArrivalTimeDto arrivalTime;
    @Nullable
    private Set<String> amenities;
}
