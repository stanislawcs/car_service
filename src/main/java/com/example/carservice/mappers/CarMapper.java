package com.example.carservice.mappers;

import com.example.carservice.domain.Car;
import com.example.carservice.dto.CarDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CarMapper implements Mapper<Car,CarDTO> {

    private final ModelMapper modelMapper;

    public CarMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Car toEntity(CarDTO dto){
        return modelMapper.map(dto,Car.class);
    }

    @Override
    public CarDTO toDTO(Car entity){
        return modelMapper.map(entity,CarDTO.class);
    }
}
