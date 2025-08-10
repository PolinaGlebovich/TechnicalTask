package com.example.technicaltask.mapper;

import com.example.technicaltask.dto.HotelGeneralDto;
import com.example.technicaltask.entity.Address;
import com.example.technicaltask.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HotelGeneralMapper {
    @Mapping(target = "address", expression = "java(formatAddress(hotel.getAddress()))")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelGeneralDto toDto(Hotel hotel);

    default String formatAddress(Address address) {
        if (address == null) return null;
        return address.getHouseNumber() + " " + address.getStreet() + ", " +
                address.getCity() + ", " + address.getPostCode() + ", " + address.getCountry();
    }
}
