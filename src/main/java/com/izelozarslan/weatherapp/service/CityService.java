package com.izelozarslan.weatherapp.service;

import com.izelozarslan.weatherapp.entity.City;
import com.izelozarslan.weatherapp.general.BaseEntityService;
import com.izelozarslan.weatherapp.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService extends BaseEntityService<City, CityRepository> {
    public CityService(CityRepository repository) {
        super(repository);
    }
}
