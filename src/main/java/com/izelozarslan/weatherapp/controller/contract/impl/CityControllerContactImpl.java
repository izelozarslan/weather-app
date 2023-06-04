package com.izelozarslan.weatherapp.controller.contract.impl;

import com.izelozarslan.weatherapp.controller.contract.CityControllerContract;
import com.izelozarslan.weatherapp.dto.city.CityDTO;
import com.izelozarslan.weatherapp.dto.city.CitySaveRequestDTO;
import com.izelozarslan.weatherapp.entity.City;
import com.izelozarslan.weatherapp.mapper.CityMapper;
import com.izelozarslan.weatherapp.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityControllerContactImpl implements CityControllerContract {

   private final CityService service;
   private final CityMapper mapper;

    @Override
    public List<CityDTO> findByUserId(Long userId) {
        List<City> cityList = service.findByUserId(userId);
        return mapper.convertToCityDtoList(cityList);

    }

    @Override
    public CityDTO save(CitySaveRequestDTO citySaveRequestDTO) {
        City city = mapper.convertToCity(citySaveRequestDTO);
        service.save(city);
        return mapper.convertToCityDto(city);
    }

    @Override
    public void delete(Long id) {
        service.delete(id);
    }
}
