package com.izelozarslan.weatherapp.errormessages;


import com.izelozarslan.weatherapp.general.BaseErrorMessage;

public enum CityErrorMessage implements BaseErrorMessage {

    CITY_NOT_FOUND_WITH_ID("City not found with id: "),
    CITY_NOT_CREATED_WITH_USER_ID("City not created with user id : "),
    CITY_NOT_DELETED_WITH_ID("City not deleted with id: "),
    INVALID_CITY_NAME("City name cannot be empty");

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
