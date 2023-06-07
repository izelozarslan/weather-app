package com.izelozarslan.weatherapp.controller.contract;

import com.izelozarslan.weatherapp.dto.city.response.CityResponseDTO;
import com.izelozarslan.weatherapp.dto.city.request.CitySaveRequestDTO;

import java.util.List;

public interface CityControllerContract {



    List<CityResponseDTO> findCityByUserId();

    CityResponseDTO save(CitySaveRequestDTO citySaveRequestDTO);

    void delete(Long id);
}
