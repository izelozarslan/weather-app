package com.izelozarslan.weatherapp.errormessages;


import com.izelozarslan.weatherapp.general.BaseErrorMessage;

public enum CityErrorMessage implements BaseErrorMessage {

    CITY_NOT_FOUND("City not found");

    private final String message;

    CityErrorMessage(String message) {
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
