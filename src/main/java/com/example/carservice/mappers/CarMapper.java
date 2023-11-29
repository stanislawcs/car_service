package com.example.carservice.mappers;

import com.example.carservice.domain.Car;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.dto.CarListDTO;
import com.example.carservice.dto.converter.Response;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = TechInspectionMapper.class)
public interface CarMapper {


    @Mapping(target = "brand", source = "car.brand")
    @Mapping(target = "number", source = "car.number")
    @Mapping(target = "color", source = "car.color")
    @Mapping(target = "price", source = "car.price")
    @Mapping(target = "techInspections", source = "car.techInspections")
    @Mapping(target = "usdPrice", source = "response.officialRate")
    CarDTO toDTO(Car car, Response response);

    @Mapping(target = "id", ignore = true)
    Car toEntity(CarDTO dto);

    CarListDTO toListDTO(Car car);

    @Mapping(target = "id", ignore = true)
    void toEntity(CarDTO dto, @MappingTarget Car entity);

    @AfterMapping
    default void enrichUsdPrice(@MappingTarget CarDTO carDTO, Response response) {
        if (response.getOfficialRate() == 0) {
            carDTO.setUsdPrice(0);
        } else {
            carDTO.setUsdPrice(round(carDTO.getPrice() / response.getOfficialRate()));
        }
    }

    default double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(0, RoundingMode.HALF_UP).doubleValue();
    }

}