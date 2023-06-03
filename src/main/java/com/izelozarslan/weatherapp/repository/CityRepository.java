package com.izelozarslan.weatherapp.repository;

import com.izelozarslan.weatherapp.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {

}
