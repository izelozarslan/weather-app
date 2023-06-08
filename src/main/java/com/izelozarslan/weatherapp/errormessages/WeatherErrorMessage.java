package com.izelozarslan.weatherapp.errormessages;

import com.izelozarslan.weatherapp.general.BaseErrorMessage;

public enum WeatherErrorMessage implements BaseErrorMessage {
    WEATHER_NOT_FOUND("Weather not found"),
    WEATHER_DATA_PARSING_ERROR("Weather data parsing error : ");
    private final String message;

    WeatherErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
