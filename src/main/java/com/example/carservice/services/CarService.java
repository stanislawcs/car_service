package com.example.carservice.services;

import com.example.carservice.dto.CarDTO;
import com.example.carservice.dto.CarListDTO;
import com.example.carservice.dto.CreationResponse;

import java.util.List;

public interface CarService {
    CarDTO getOneById(Long id);

    void update(CarDTO car, Long id);

    CreationResponse create(CarDTO carDTO);

    void delete(Long id);

    List<CarListDTO> getAll();
}
