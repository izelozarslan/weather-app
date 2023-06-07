package com.izelozarslan.weatherapp.service;

import com.izelozarslan.weatherapp.entity.City;
import com.izelozarslan.weatherapp.general.BaseEntityService;
import com.izelozarslan.weatherapp.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityEntityService extends BaseEntityService<City, CityRepository> {

    private CityRepository repository;

    public CityEntityService(CityRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<City> findCitiesByUserId(Long userId){
        return repository.findCitiesByUserId(userId);
    }

}
