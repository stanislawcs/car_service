package com.example.carservice.services;

import com.example.carservice.domain.Car;

public interface CarService {
    Car getOneById(Long id);

    void update(Car car,Long id);

    void save(Car car);

    void delete(Long id);
}
