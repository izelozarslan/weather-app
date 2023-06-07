package com.izelozarslan.weatherapp.exception.userexceptions.exceptionhandler;


import com.izelozarslan.weatherapp.dto.user.errorresponse.UserExceptionResponse;
import com.izelozarslan.weatherapp.exception.userexceptions.exception.AuthenticationFailedException;
import com.izelozarslan.weatherapp.exception.userexceptions.exception.UserNotCreatedException;
import com.izelozarslan.weatherapp.exception.userexceptions.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class UserExceptionsHandler {

    @ExceptionHandler(UserNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserExceptionResponse handleUserNotCreatedException(UserNotCreatedException userNotCreatedException) {

        return errorResponseSetter(LocalDateTime.now(), userNotCreatedException.getMessage(), HttpStatus.BAD_REQUEST.value());
    }


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UserExceptionResponse handleUserNotFoundException(UserNotFoundException userNotFoundException) {

        return errorResponseSetter(LocalDateTime.now(), userNotFoundException.getMessage(), HttpStatus.NOT_FOUND.value());

    }

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public UserExceptionResponse handleUserNotFoundException(AuthenticationFailedException authenticationFailedException) {

        return errorResponseSetter(LocalDateTime.now(), authenticationFailedException.getMessage(), HttpStatus.UNAUTHORIZED.value());

    }


    private UserExceptionResponse errorResponseSetter(LocalDateTime localDateTime, String message, int statusCode) {
        return UserExceptionResponse.builder()
                .timeStamp(localDateTime)
                .message(message)
                .statusCode(statusCode)
                .build();
    }
}
