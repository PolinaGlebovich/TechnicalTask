package com.example.technicaltask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelGeneralDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
