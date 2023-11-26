package com.example.carservice.services.impl;

import com.example.carservice.domain.Car;
import com.example.carservice.domain.TechInspection;
import com.example.carservice.domain.exceptions.CarNotFoundException;
import com.example.carservice.dto.CarCreationResponse;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.dto.CarListDTO;
import com.example.carservice.dto.TechInspectionDTO;
import com.example.carservice.mappers.CarMapper;
import com.example.carservice.mappers.TechInspectionMapper;
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
    private final CarMapper mapper;
    private final TechInspectionMapper techMapper;


    @Override
    public CarDTO getOneById(Long id) {
        Car car = carRepository.findById(id).
                orElseThrow(() -> new CarNotFoundException("Car not found"));
        CarDTO carDTO = mapper.toDTO(car);

        List<TechInspectionDTO> inspectionsDTO = car.getTechInspections().stream().map(techMapper::toDTO).toList();
        carDTO.setTechInspections(inspectionsDTO);

        //System.out.println(StructMapper.INSTANCE.toDTO(car));
        return carDTO;
    }

    @Override
    @Transactional
    public void update(CarDTO carDTO, Long id) {
        Car car = connectCarsAndInspections(carDTO);
        car.setId(id);
        carRepository.save(car);
    }

    @Override
    @Transactional
    public CarCreationResponse create(CarDTO carDTO) {
        Car car = connectCarsAndInspections(carDTO);
        carRepository.save(car);

        CarCreationResponse response = new CarCreationResponse();
        response.setId(car.getId());
        return response;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarListDTO> getAll() {
        return carRepository.findAll().stream()
                .map(mapper::toListDTO).toList();
    }

    private Car connectCarsAndInspections(final CarDTO carDTO){
        Car car = mapper.toEntity(carDTO);

        if(carDTO.getTechInspections() != null){

            List<TechInspection> inspections = carDTO.getTechInspections().stream()
                    .map(ti->{
                        TechInspection inspection = techMapper.toEntity(ti);
                        inspection.setCar(car);
                        return inspection;
                    }).toList();

            car.setTechInspections(inspections);
        }

        return car;
    }

}
