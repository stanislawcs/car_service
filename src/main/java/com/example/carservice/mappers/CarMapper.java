package com.example.carservice.mappers;

import com.example.carservice.domain.Car;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.dto.CarListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDTO toDTO(Car car);

    default List<String> mapServices(String services) {

        return Arrays.asList(services.split(","));
    }

    default String mapServices(List<String> services){
        return String.join(",", services);
    }

    Car toEntity(CarDTO carDTO);

    CarListDTO toListDTO(Car car);

}

