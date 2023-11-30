package com.example.carservice.services.impl;

import com.example.carservice.domain.Car;
import com.example.carservice.domain.TechInspection;
import com.example.carservice.domain.exceptions.CarNotFoundException;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.dto.CarListDTO;
import com.example.carservice.dto.CreationResponse;
import com.example.carservice.mappers.CarMapper;
import com.example.carservice.mappers.TechInspectionMapper;
import com.example.carservice.repositories.CarRepository;
import com.example.carservice.services.CarService;
import com.example.carservice.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CurrencyService currencyService;
    private final CarMapper carMapper;
    private final TechInspectionMapper techInspectionMapper;


    @Override
    public CarDTO getOneById(Long id) {
        Car car = carRepository.findById(id).
                orElseThrow(() -> new CarNotFoundException("Car not found"));

        return carMapper.toDTO(car, currencyService.getCurrencyRate());
    }

    @Override
    @Transactional
    public void update(CarDTO carDTO, Long id) {
        Car carToUpdate = carRepository.findById(id).
                orElseThrow(() -> new CarNotFoundException("Car not found"));

        carMapper.toEntity(carDTO, carToUpdate);
        carToUpdate.getTechInspections().forEach(ti -> ti.setCar(carToUpdate));

        carRepository.save(carToUpdate);
    }

    @Override
    @Transactional
    public CreationResponse create(CarDTO carDTO) {
        Car car = connectCarsAndInspections(carDTO);
        carRepository.save(car);

        return new CreationResponse(car.getId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarListDTO> getAll() {
        return carRepository.findAll().stream()
                .map(carMapper::toListDTO).toList();
    }

    private Car connectCarsAndInspections(final CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);

        if (carDTO.getTechInspections() != null) {

            List<TechInspection> inspections = carDTO.getTechInspections().stream()
                    .map(ti -> {
                        TechInspection inspection = techInspectionMapper.toEntity(ti);
                        if (car.getTechInspections() == null)
                            car.setTechInspections(new ArrayList<>());
                        car.getTechInspections().add(inspection);
                        inspection.setCar(car);
                        return inspection;
                    }).toList();

            car.setTechInspections(inspections);
        }

        return car;
    }

}
