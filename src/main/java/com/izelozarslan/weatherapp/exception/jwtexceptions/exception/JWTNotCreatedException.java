package com.izelozarslan.weatherapp.exception.jwtexceptions.exception;

public class JWTNotCreatedException extends RuntimeException{

    public JWTNotCreatedException(String message) {
        super(message);
    }
}
