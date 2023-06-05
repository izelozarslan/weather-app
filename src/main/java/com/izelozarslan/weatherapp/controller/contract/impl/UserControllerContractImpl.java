package com.izelozarslan.weatherapp.controller.contract.impl;

import com.izelozarslan.weatherapp.controller.contract.UserControllerContract;
import com.izelozarslan.weatherapp.dto.user.UserDTO;
import com.izelozarslan.weatherapp.dto.user.UserSaveRequestDTO;
import com.izelozarslan.weatherapp.entity.User;
import com.izelozarslan.weatherapp.errormessages.UserErrorMessage;
import com.izelozarslan.weatherapp.general.BusinessException;
import com.izelozarslan.weatherapp.mapper.UserMapper;
import com.izelozarslan.weatherapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserControllerContractImpl implements UserControllerContract {

    private final UserService service;
    private final UserMapper mapper;

    @Override
    public UserDTO save(UserSaveRequestDTO userSaveRequestDTO) {
        String username = userSaveRequestDTO.username();
        boolean isUsernameTaken = service.existsByUsername(username);
        if (isUsernameTaken) {
            throw new BusinessException(UserErrorMessage.USERNAME_ALREADY_TAKEN);
        }
        User user = mapper.convertToUser(userSaveRequestDTO);

        service.save(user);
        return mapper.convertToUserDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> userList = service.findAll();
        return mapper.convertToUserDtoList(userList);
    }
}
