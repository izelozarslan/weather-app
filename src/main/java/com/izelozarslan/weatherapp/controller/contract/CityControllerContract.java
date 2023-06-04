package com.izelozarslan.weatherapp.controller.contract;

import com.izelozarslan.weatherapp.dto.city.CityDTO;
import com.izelozarslan.weatherapp.dto.city.CitySaveRequestDTO;

import java.util.List;

public interface CityControllerContract {

    List<CityDTO> findByUserId(Long userId);

    CityDTO save(CitySaveRequestDTO citySaveRequestDTO);

    void delete(Long id);
}
