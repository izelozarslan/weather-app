package com.izelozarslan.weatherapp.controller;

import com.izelozarslan.weatherapp.controller.contract.UserControllerContract;
import com.izelozarslan.weatherapp.dto.user.response.UserResponseDTO;
import com.izelozarslan.weatherapp.general.RestResponse;
import com.izelozarslan.weatherapp.openweathermap.WeatherData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserControllerContract userControllerContract;

    @GetMapping
    public ResponseEntity<RestResponse<List<UserResponseDTO>>> findAll(){
        List<UserResponseDTO> userResponseDTOList = userControllerContract.findAll();
        return ResponseEntity.ok(RestResponse.of(userResponseDTOList));
    }

    @GetMapping("/user/cities/{unit}")
    public ResponseEntity<RestResponse<Map<String, WeatherData>>> findUsersSavedCitiesWeatherData(@PathVariable String unit){
        Map<String, WeatherData> citiesWeatherData = userControllerContract.findUsersSavedCitiesWeatherData(unit);
        return ResponseEntity.ok(RestResponse.of(citiesWeatherData));
    }
}
