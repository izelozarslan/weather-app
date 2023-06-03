package com.izelozarslan.weatherapp.service;

import com.izelozarslan.weatherapp.entity.User;
import com.izelozarslan.weatherapp.general.BaseEntityService;
import com.izelozarslan.weatherapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseEntityService<User, UserRepository> {
    public UserService(UserRepository repository) {
        super(repository);
    }
}
