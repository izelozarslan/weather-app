package com.izelozarslan.weatherapp.repository;

import com.izelozarslan.weatherapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
