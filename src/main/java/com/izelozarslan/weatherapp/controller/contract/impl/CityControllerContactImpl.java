package com.izelozarslan.weatherapp.controller.contract.impl;

import com.izelozarslan.weatherapp.controller.contract.CityControllerContract;
import com.izelozarslan.weatherapp.dto.city.response.CityResponseDTO;
import com.izelozarslan.weatherapp.dto.city.request.CitySaveRequestDTO;
import com.izelozarslan.weatherapp.entity.City;
import com.izelozarslan.weatherapp.errormessages.CityErrorMessage;
import com.izelozarslan.weatherapp.exception.cityexceptions.exception.CityNotCreatedException;
import com.izelozarslan.weatherapp.exception.cityexceptions.exception.CityNotDeletedException;
import com.izelozarslan.weatherapp.exception.cityexceptions.exception.CityNotFoundException;
import com.izelozarslan.weatherapp.mapper.CityMapper;
import com.izelozarslan.weatherapp.security.user.User;
import com.izelozarslan.weatherapp.service.CityEntityService;
import com.izelozarslan.weatherapp.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityControllerContactImpl implements CityControllerContract {

    private final CityEntityService service;
    private final CityMapper mapper;
    private final UserEntityService userEntityService;

    @Override
    public List<CityResponseDTO> findByUserId(Long userId) {
        List<City> cityList = service.findByUserId(userId);
        if (!cityList.isEmpty()) {
            return mapper.convertToCityDtoList(cityList);
        } else {
            throw new CityNotFoundException(CityErrorMessage.CITY_NOT_FOUND_WITH_ID.getMessage() + userId);
        }
    }

    @Override
    public List<CityResponseDTO> findCityByUserId() {
        User user = userEntityService.extractUser();

        List<City> cityList = service.findByUserId(user.getId());
        if (!cityList.isEmpty()) {
            return mapper.convertToCityDtoList(cityList);
        } else {
            throw new CityNotFoundException(CityErrorMessage.CITY_NOT_FOUND_WITH_ID.getMessage() + user.getId());
        }
    }

    @Override
    public CityResponseDTO save(CitySaveRequestDTO citySaveRequestDTO) {
        City city = mapper.convertToCity(citySaveRequestDTO);
        User user = userEntityService.extractUser();
        //TODO şehri boş gönderemezsiniz hatası
        try {
            city.setUser(user);
            service.save(city);
            //TODO: log
            return mapper.convertToCityDto(city);
        }catch (Exception e){
            throw  new CityNotCreatedException(CityErrorMessage.CITY_NOT_CREATED_WITH_USER_ID.getMessage() + user.getId());
        }

    }

    @Override
    public void delete(Long id) {
        Optional<City> city = service.findById(id);
        if(city.isPresent()) {
            service.delete(id);
        }else{
            throw new CityNotDeletedException(CityErrorMessage.CITY_NOT_DELETED_WITH_ID.getMessage() + id);
        }


    }
}
