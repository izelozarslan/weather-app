package com.izelozarslan.weatherapp.exception.userexceptions.exception;

public class AuthenticationFailedException extends RuntimeException{

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
