package com.izelozarslan.weatherapp.controller.contract;

import com.izelozarslan.weatherapp.dto.user.UserDTO;
import com.izelozarslan.weatherapp.dto.user.UserSaveRequestDTO;

import java.util.List;

public interface UserControllerContract {

    UserDTO save(UserSaveRequestDTO userSaveRequestDTO);

    List<UserDTO> findAll();
}
