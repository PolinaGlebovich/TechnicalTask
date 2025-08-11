package com.example.technicaltask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ArrivalTimeDto {
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "Check-in time is required")
    private LocalTime checkIn;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime checkOut;
}
