package com.example.carservice.services;

import com.example.carservice.dto.CarDTO;

import java.util.List;

public interface CarService {
    CarDTO getOneById(Long id);

    void update(CarDTO car, Long id);

    void save(CarDTO carDTO);

    void delete(Long id);

    List<CarDTO> getAll();
}
