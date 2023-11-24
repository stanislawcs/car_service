package com.example.carservice.services;

import com.example.carservice.dto.CarCreationResponse;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.dto.CarListDTO;

import java.util.List;

public interface CarService {
    CarDTO getOneById(Long id);

    void update(CarDTO car, Long id);

    CarCreationResponse create(CarDTO carDTO);

    void delete(Long id);

    List<CarListDTO> getAll();
}
