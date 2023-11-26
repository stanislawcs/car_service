package com.example.carservice.mappers;

import com.example.carservice.domain.Car;
import com.example.carservice.dto.CarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CarMapperStruct {

    CarMapperStruct INSTANCE = Mappers.getMapper(CarMapperStruct.class);

    CarDTO toDTO(Car car);

    default List<String> mapServices(String services) {

        return Arrays.asList(services.split(","));
    }
}
