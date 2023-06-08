package com.izelozarslan.weatherapp.controller.contract;

import com.izelozarslan.weatherapp.dto.user.response.UserResponseDTO;
import com.izelozarslan.weatherapp.openweathermap.WeatherData;

import java.util.List;
import java.util.Map;

public interface UserControllerContract {

    Map<String, WeatherData> findUsersSavedCitiesWeatherData(String unit);
    List<UserResponseDTO> findAll();
}
