package com.example.technicaltask.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table (name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String brand;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToOne
    @JoinColumn(name = "contacts_id")
    private Contacts contacts;
    @OneToOne
    @JoinColumn(name = "arrival_time_id")
    private ArrivalTime arrivalTime;
    @ElementCollection
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private Set<String> amenities;

}
