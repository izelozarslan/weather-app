package com.izelozarslan.weatherapp.controller;

import com.izelozarslan.weatherapp.controller.contract.UserControllerContract;
import com.izelozarslan.weatherapp.dto.user.response.UserResponseDTO;
import com.izelozarslan.weatherapp.general.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
