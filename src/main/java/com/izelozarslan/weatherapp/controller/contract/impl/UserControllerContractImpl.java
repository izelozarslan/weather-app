package com.izelozarslan.weatherapp.controller.contract.impl;

import com.izelozarslan.weatherapp.controller.contract.UserControllerContract;
import com.izelozarslan.weatherapp.dto.user.response.UserResponseDTO;
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



    @Override
    public List<UserResponseDTO> findAll() {
        List<User> userList = service.findAll();
        kafkaService.sendMessageInfo("Users returned!", "logs");
        return mapper.convertToUserDtoList(userList);
    }
}
