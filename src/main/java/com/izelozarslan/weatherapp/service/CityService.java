package com.izelozarslan.weatherapp.service;

import com.izelozarslan.weatherapp.entity.City;
import com.izelozarslan.weatherapp.general.BaseEntityService;
import com.izelozarslan.weatherapp.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService extends BaseEntityService<City, CityRepository> {

    private CityRepository repository;

    public CityService(CityRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<City> findByUserId(Long userId){
        return repository.findCitiesByUserId(userId);
    }

}
