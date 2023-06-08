package com.izelozarslan.weatherapp.errormessages;


import com.izelozarslan.weatherapp.general.BaseErrorMessage;

public enum JwtErrorMessage implements BaseErrorMessage {


    JWT_GENERATION_FAILED("JWT token generation failed : ");

    private final String message;

    JwtErrorMessage(String message) {
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
