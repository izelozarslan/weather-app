package com.izelozarslan.weatherapp.controller.contract.impl;

import com.izelozarslan.weatherapp.controller.contract.UserControllerContract;
import com.izelozarslan.weatherapp.dto.user.UserDTO;
import com.izelozarslan.weatherapp.kafka.service.KafkaService;
import com.izelozarslan.weatherapp.mapper.UserMapper;
import com.izelozarslan.weatherapp.security.user.User;
import com.izelozarslan.weatherapp.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserControllerContractImpl implements UserControllerContract {

    private final UserEntityService service;
    private final UserMapper mapper;
    private final KafkaService kafkaService;

//    @Override
//    public UserDTO save(UserSaveRequestDTO userSaveRequestDTO) {
//        String username = userSaveRequestDTO.username();
//
//        boolean isUsernameTaken = service.existsByUsername(username);
//        if (isUsernameTaken) {
//            throw new BusinessException(UserErrorMessage.USERNAME_ALREADY_TAKEN);
//        }
//        User user = mapper.convertToUser(userSaveRequestDTO);
//
//        service.save(user);
//        kafkaService.sendMessage("User saved!", "logs");
//        return mapper.convertToUserDTO(user);
//    }

    @Override
    public List<UserDTO> findAll() {
        List<User> userList = service.findAll();
        kafkaService.sendMessage("Users returned!", "logs");
        return mapper.convertToUserDtoList(userList);
    }
}
