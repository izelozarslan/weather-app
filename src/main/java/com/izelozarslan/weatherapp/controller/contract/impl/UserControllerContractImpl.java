package com.izelozarslan.weatherapp.controller.contract.impl;

import com.izelozarslan.weatherapp.controller.contract.UserControllerContract;
import com.izelozarslan.weatherapp.dto.user.response.UserResponseDTO;
import com.izelozarslan.weatherapp.entity.City;
import com.izelozarslan.weatherapp.errormessages.WeatherErrorMessage;
import com.izelozarslan.weatherapp.exception.weatherexceptions.exception.WeatherDataNotFoundException;
import com.izelozarslan.weatherapp.kafka.service.KafkaService;
import com.izelozarslan.weatherapp.mapper.UserMapper;
import com.izelozarslan.weatherapp.openweathermap.WeatherData;
import com.izelozarslan.weatherapp.openweathermap.WeatherService;
import com.izelozarslan.weatherapp.security.user.User;
import com.izelozarslan.weatherapp.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserControllerContractImpl implements UserControllerContract {

    private final UserEntityService service;
    private final UserMapper mapper;
    private final KafkaService kafkaService;
    private final WeatherService weatherService;



    @Override
    public List<UserResponseDTO> findAll() {
        List<User> userList = service.findAll();
        kafkaService.sendMessageInfo("Users returned!", "logs");
        return mapper.convertToUserDtoList(userList);
    }

    @Override
    public Map<String, WeatherData> findUsersSavedCitiesWeatherData(String unit) {
        User user = service.extractUser();
        List<City> cities = user.getCities();
        Map<String, WeatherData> citiesData = new HashMap<>();

        for (City city : cities) {
            try{
                citiesData.put(city.getName(), weatherService.getWeatherForecast(city.getName(),unit));
            }
            catch (Exception e){
                throw new WeatherDataNotFoundException(WeatherErrorMessage.WEATHER_NOT_FOUND.getMessage());
            }
        }
        kafkaService.sendMessageInfo("Weather data returned!", "logs");
        return citiesData;
    }
}
