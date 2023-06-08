package com.izelozarslan.weatherapp.dto.jwt.errorresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTExceptionResponse {

    private LocalDateTime timeStamp;
    private String message;
    private int statusCode;
}
