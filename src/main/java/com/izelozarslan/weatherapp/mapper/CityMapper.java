package com.izelozarslan.weatherapp.mapper;

import com.izelozarslan.weatherapp.dto.city.response.CityResponseDTO;
import com.izelozarslan.weatherapp.dto.city.request.CitySaveRequestDTO;
import com.izelozarslan.weatherapp.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    List<CityResponseDTO> convertToCityDtoList(List<City> cityList);

    @Mapping(target = "userId", source = "user.id")
//    @Mapping(target = "baseAdditionalFields", ignore = true)
//    @Mapping(target = "id", ignore = true)
    CityResponseDTO convertToCityDto(City city);

    //@Mapping(target = "user.id", source = "userId")
//    @Mapping(target = "baseAdditionalFields", ignore = true)
//    @Mapping(target = "id", ignore = true)
    City convertToCity(CitySaveRequestDTO citySaveRequestDTO);
}
