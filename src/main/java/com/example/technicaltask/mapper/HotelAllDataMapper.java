package com.example.technicaltask.mapper;

import com.example.technicaltask.dto.HotelAllDataDto;
import com.example.technicaltask.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressMapper.class, ContactsMapper.class, ArrivalTimeMapper.class})
public interface HotelAllDataMapper {
    HotelAllDataDto toDto(Hotel hotel);
}
