package com.izelozarslan.weatherapp.repository;

import com.izelozarslan.weatherapp.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Long> {

    List<City> findCitiesByUserId(Long userId);


}
