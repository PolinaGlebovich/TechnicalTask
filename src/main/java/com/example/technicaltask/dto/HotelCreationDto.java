package com.example.technicaltask.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HotelCreationDto {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    @NotBlank(message = "Brand is required")
    private String brand;
    @Valid
    @NotNull(message = "Address is required")
    private AddressDto address;
    @Valid
    @NotNull(message = "Contacts are required")
    private ContactsDto contacts;
    @Valid
    @NotNull(message = "ArrivalTime is required")
    private ArrivalTimeDto arrivalTime;
}
