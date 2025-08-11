package com.example.technicaltask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDto {
    @NotNull(message = "House number is required")
    private int houseNumber;
    @NotBlank(message = "Street is required")
    private String street;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "Postcode is required")
    private String postCode;
}
