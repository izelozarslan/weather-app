package com.izelozarslan.weatherapp.openweathermap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izelozarslan.weatherapp.errormessages.WeatherErrorMessage;
import com.izelozarslan.weatherapp.general.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public WeatherData getWeatherForecast(String cityName, String unit){
        String weatherForecast = weatherClient.getWeatherForecast(cityName, apiKey, unit);
        return parseWeatherData(weatherForecast);
    }


    private WeatherData parseWeatherData(String weatherForecast) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(weatherForecast, WeatherData.class);
        }
        catch (JsonProcessingException e) {
            throw new BusinessException(WeatherErrorMessage.WEATHER_DATA_PARSING_ERROR);
        }
    }

}
