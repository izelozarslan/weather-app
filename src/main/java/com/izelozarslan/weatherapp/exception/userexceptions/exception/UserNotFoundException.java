package com.izelozarslan.weatherapp.exception.userexceptions.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
