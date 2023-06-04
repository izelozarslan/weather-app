package com.izelozarslan.weatherapp.mapper;

import com.izelozarslan.weatherapp.dto.city.CityDTO;
import com.izelozarslan.weatherapp.dto.city.CitySaveRequestDTO;
import com.izelozarslan.weatherapp.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    List<CityDTO> convertToCityDtoList(List<City> cityList);

    @Mapping(target = "userId", source = "user.id")
//    @Mapping(target = "baseAdditionalFields", ignore = true)
//    @Mapping(target = "id", ignore = true)
    CityDTO convertToCityDto(City city);

    @Mapping(target = "user.id", source = "userId")
//    @Mapping(target = "baseAdditionalFields", ignore = true)
//    @Mapping(target = "id", ignore = true)
    City convertToCity(CitySaveRequestDTO citySaveRequestDTO);
}
