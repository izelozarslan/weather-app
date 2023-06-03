package com.izelozarslan.weatherapp.openweathermap;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping("/data")
    public WeatherData getWeather(@RequestParam String cityName , @RequestParam String unit) {
        return weatherService.getWeatherForecast(cityName, unit);
    }
}
