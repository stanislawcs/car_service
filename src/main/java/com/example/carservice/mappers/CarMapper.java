package com.example.carservice.mappers;

import com.example.carservice.domain.Car;
import com.example.carservice.dto.CarDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CarMapper{

    private final ModelMapper modelMapper;

    public CarMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Car toEntity(CarDTO carDTO){
        return modelMapper.map(carDTO,Car.class);
    }

    public CarDTO toDTO(Car car){
        return modelMapper.map(car,CarDTO.class);
    }
}
