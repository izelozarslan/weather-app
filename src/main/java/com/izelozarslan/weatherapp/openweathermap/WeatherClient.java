package com.izelozarslan.weatherapp.openweathermap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "weather" , url="api.openweathermap.org/data/2.5/forecast")
public interface WeatherClient {

    @GetMapping
    String getWeatherForecast(@RequestParam("q") String cityName,
                              @RequestParam("appid") String apiKey,
                              @RequestParam("units") String unit );

}
