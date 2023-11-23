package com.example.carservice.services.impl;

import com.example.carservice.domain.Car;
import com.example.carservice.domain.exceptions.CarNotFoundException;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.mappers.Mapper;
import com.example.carservice.repositories.CarRepository;
import com.example.carservice.services.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final Mapper<Car, CarDTO> mapper;

    @Override
    public CarDTO getOneById(Long id) {
        return mapper.toDTO(carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car was not found")));
    }

    @Override
    @Transactional
    public void update(CarDTO carDTO, Long id) {
        Car car = mapper.toEntity(carDTO);
        car.setId(id);
        carRepository.save(car);
    }

    @Override
    @Transactional
    public void save(CarDTO carDTO) {
        Car car = mapper.toEntity(carDTO);
        carRepository.save(car);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarDTO> getAll() {
        return carRepository.findAll().stream()
                .map(mapper::toDTO).toList();
    }

}
