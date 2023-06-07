package com.izelozarslan.weatherapp.controller.contract;

import com.izelozarslan.weatherapp.dto.user.response.UserResponseDTO;

import java.util.List;

public interface UserControllerContract {


    List<UserResponseDTO> findAll();
}
