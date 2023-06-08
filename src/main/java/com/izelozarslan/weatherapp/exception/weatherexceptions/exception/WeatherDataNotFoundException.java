package com.izelozarslan.weatherapp.exception.weatherexceptions.exception;

public class WeatherDataNotFoundException extends RuntimeException{

    public WeatherDataNotFoundException(String message) {
        super(message);
    }
}
