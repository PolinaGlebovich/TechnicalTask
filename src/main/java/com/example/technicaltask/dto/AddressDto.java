package com.example.technicaltask.dto;

import lombok.Data;

@Data
public class AddressDto {
    private int houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
