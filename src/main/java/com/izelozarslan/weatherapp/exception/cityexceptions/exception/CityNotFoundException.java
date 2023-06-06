package com.izelozarslan.weatherapp.exception.cityexceptions.exception;

public class CityNotFoundException extends RuntimeException{

    public CityNotFoundException(String message) {
        super(message);
    }
}
