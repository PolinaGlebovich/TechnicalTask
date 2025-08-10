package com.example.technicaltask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "arrival_time")
public class ArrivalTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime checkIn;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime checkOut;

}
