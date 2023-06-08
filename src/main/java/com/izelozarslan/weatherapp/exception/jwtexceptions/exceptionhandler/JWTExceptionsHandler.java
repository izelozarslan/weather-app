package com.izelozarslan.weatherapp.exception.jwtexceptions.exceptionhandler;


import com.izelozarslan.weatherapp.dto.jwt.errorresponse.JWTExceptionResponse;
import com.izelozarslan.weatherapp.exception.jwtexceptions.exception.JWTNotCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class JWTExceptionsHandler {

    @ExceptionHandler(JWTNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JWTExceptionResponse handleJWTNotCreatedException(JWTNotCreatedException JWTNotCreatedException) {

        return errorResponseSetter(LocalDateTime.now(), JWTNotCreatedException.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    private JWTExceptionResponse errorResponseSetter(LocalDateTime localDateTime, String message, int statusCode) {
        return JWTExceptionResponse.builder()
                .timeStamp(localDateTime)
                .message(message)
                .statusCode(statusCode)
                .build();
    }
}
