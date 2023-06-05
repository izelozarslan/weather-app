package com.izelozarslan.weatherapp.mapper;

import com.izelozarslan.weatherapp.dto.user.UserDTO;
import com.izelozarslan.weatherapp.dto.user.UserSaveRequestDTO;
import com.izelozarslan.weatherapp.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User convertToUser(UserSaveRequestDTO userSaveRequestDTO);

    UserDTO convertToUserDTO(User user);

    List<UserDTO> convertToUserDtoList(List<User> userList);
}
