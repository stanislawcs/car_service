package com.example.carservice.services;

import com.example.carservice.domain.Car;
import com.example.carservice.dto.CarDTO;

public interface CarService {
    CarDTO getOneById(Long id);

    void update(CarDTO car,Long id);

    void save(CarDTO carDTO);

    void delete(Long id);
}
