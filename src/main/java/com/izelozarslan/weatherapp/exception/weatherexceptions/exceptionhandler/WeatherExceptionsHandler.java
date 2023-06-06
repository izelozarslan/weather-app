package com.izelozarslan.weatherapp.exception.weatherexceptions.exceptionhandler;

import com.izelozarslan.weatherapp.errormessages.WeatherErrorMessage;
import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WeatherExceptionsHandler {

    @ExceptionHandler(FeignException.class)
    public String handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return WeatherErrorMessage.WEATHER_NOT_FOUND.getMessage();
    }
}
