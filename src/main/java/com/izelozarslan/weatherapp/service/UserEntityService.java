package com.izelozarslan.weatherapp.service;

import com.izelozarslan.weatherapp.entity.City;
import com.izelozarslan.weatherapp.errormessages.UserErrorMessage;
import com.izelozarslan.weatherapp.exception.userexceptions.exception.UserNotFoundException;
import com.izelozarslan.weatherapp.general.BaseEntityService;
import com.izelozarslan.weatherapp.security.user.User;
import com.izelozarslan.weatherapp.security.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntityService extends BaseEntityService<User, UserRepository> {
    private final UserRepository repository;

    public UserEntityService(UserRepository repository) {
        super(repository);
        this.repository=repository;
    }


    public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }

    public User findByEmail(String email){
        return repository.findByEmail(email).orElseThrow();
    }

    public List<City> getSavedCitiesOfUser(){
        User user = extractUser();
        return user.getCities();
    }

    public User extractUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = repository.findByEmail(userDetails.getUsername()).orElseThrow();
            return user;
        } else {
            throw new UserNotFoundException(UserErrorMessage.USERS_NOT_FOUND.getMessage());
        }
    }
}
