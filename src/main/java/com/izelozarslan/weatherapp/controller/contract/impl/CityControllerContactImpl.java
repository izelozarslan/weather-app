package com.izelozarslan.weatherapp.controller.contract.impl;

import com.izelozarslan.weatherapp.controller.contract.CityControllerContract;
import com.izelozarslan.weatherapp.dto.city.request.CitySaveRequestDTO;
import com.izelozarslan.weatherapp.dto.city.response.CityResponseDTO;
import com.izelozarslan.weatherapp.entity.City;
import com.izelozarslan.weatherapp.errormessages.CityErrorMessage;
import com.izelozarslan.weatherapp.exception.cityexceptions.exception.CityNotCreatedException;
import com.izelozarslan.weatherapp.exception.cityexceptions.exception.CityNotDeletedException;
import com.izelozarslan.weatherapp.exception.cityexceptions.exception.CityNotFoundException;
import com.izelozarslan.weatherapp.kafka.service.KafkaService;
import com.izelozarslan.weatherapp.mapper.CityMapper;
import com.izelozarslan.weatherapp.security.user.User;
import com.izelozarslan.weatherapp.service.CityEntityService;
import com.izelozarslan.weatherapp.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityControllerContactImpl implements CityControllerContract {

    private final CityEntityService service;
    private final CityMapper mapper;
    private final UserEntityService userEntityService;
    private final KafkaService kafkaService;


    @Override
    public List<CityResponseDTO> findCityByUserId() {
        User user = userEntityService.extractUser();

        List<City> cityList = service.findCitiesByUserId(user.getId());
        if (!cityList.isEmpty()) {
            List<String> cityNames = cityList.stream().map(City::getName).collect(Collectors.toList());
            kafkaService.sendMessageInfo("City found : " + cityNames, "logs");

            return mapper.convertToCityDtoList(cityList);
        } else {
            kafkaService.sendMessageError("Error finding city !", "logs");

            throw new CityNotFoundException(CityErrorMessage.CITY_NOT_FOUND_WITH_ID.getMessage() + user.getId());
        }
    }

    @Transactional
    @Override
    public CityResponseDTO save(CitySaveRequestDTO citySaveRequestDTO) {

        City city = mapper.convertToCity(citySaveRequestDTO);

        if (city.getName() == null || city.getName().isEmpty()) {
            kafkaService.sendMessageError("City name cannot be empty : "+ city.getName(), "logs");

            throw new CityNotCreatedException(CityErrorMessage.INVALID_CITY_NAME.getMessage());
        }

        User user = userEntityService.extractUser();
        try {
            city.setUser(user);
            service.save(city);

            kafkaService.sendMessageInfo("City saved : " + city.getName(), "logs");
            return mapper.convertToCityDto(city);
        } catch (Exception e) {
            kafkaService.sendMessageError("Error saving city : "+ city.getName(), "logs");
            throw new CityNotCreatedException(CityErrorMessage.CITY_NOT_CREATED_WITH_USER_ID.getMessage() + user.getId());
        }

    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<City> cityOptional  = service.findById(id);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            kafkaService.sendMessageInfo("City deleted : " + city.getName(), "logs");

            service.delete(id);

        } else {
            kafkaService.sendMessageError("Error deleting city !", "logs");

            throw new CityNotDeletedException(CityErrorMessage.CITY_NOT_DELETED_WITH_ID.getMessage() + id);
        }
    }
}
