package com.izelozarslan.weatherapp.mapper;

import com.izelozarslan.weatherapp.dto.user.UserDTO;
import com.izelozarslan.weatherapp.security.auth.dto.AuthenticationRequestDTO;
import com.izelozarslan.weatherapp.security.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User convertToUser(AuthenticationRequestDTO authenticationRequestDTO);

    UserDTO convertToUserDTO(User user);

    List<UserDTO> convertToUserDtoList(List<User> userList);
}
