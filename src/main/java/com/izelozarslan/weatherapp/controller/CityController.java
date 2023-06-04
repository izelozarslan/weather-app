package com.izelozarslan.weatherapp.controller;

import com.izelozarslan.weatherapp.controller.contract.CityControllerContract;
import com.izelozarslan.weatherapp.dto.city.CityDTO;
import com.izelozarslan.weatherapp.dto.city.CitySaveRequestDTO;
import com.izelozarslan.weatherapp.general.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityControllerContract cityControllerContract;

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<List<CityDTO>>> findByUserId(@PathVariable Long id){
        List<CityDTO> cityDTOList = cityControllerContract.findByUserId(id);
        return ResponseEntity.ok(RestResponse.of(cityDTOList));
    }


    @PostMapping
    public ResponseEntity<RestResponse<CityDTO>> save(@RequestBody CitySaveRequestDTO citySaveRequestDTO){
        CityDTO cityDTO = cityControllerContract.save(citySaveRequestDTO);
        return ResponseEntity.ok(RestResponse.of(cityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Object>> delete(@PathVariable Long id){
        cityControllerContract.delete(id);
        return ResponseEntity.ok(RestResponse.empty());
    }

}
