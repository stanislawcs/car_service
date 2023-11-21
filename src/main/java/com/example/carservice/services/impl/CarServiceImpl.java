package com.example.carservice.services.impl;

import com.example.carservice.domain.Car;
import com.example.carservice.repositories.CarRepository;
import com.example.carservice.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public Car getOneById(Long id) {
        return carRepository.getById(id);
    }

    @Override
    public void update(Car car, Long id) {
        car.setId(id);
        carRepository.save(car);
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }


}
