package com.izelozarslan.weatherapp.dto.user.errorresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserExceptionResponse {

    private LocalDateTime timeStamp;
    private String message;
    private int statusCode;
}
