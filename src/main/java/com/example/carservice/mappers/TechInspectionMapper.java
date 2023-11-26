package com.example.carservice.mappers;

import com.example.carservice.domain.TechInspection;
import com.example.carservice.dto.TechInspectionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TechInspectionMapper {

    TechInspectionMapper INSTANCE = Mappers.getMapper(TechInspectionMapper.class);

    @Mapping(target = "carId", source = "car.id")
    TechInspectionDTO toDTO(TechInspection techInspection);

    @Mapping(target = "car.id",source = "carId")
    TechInspection toEntity(TechInspectionDTO techInspectionDTO);

    default List<String> mapServices(String services) {

        return Arrays.asList(services.split(","));
    }

    default String mapServices(List<String> services){
        return String.join(",", services);
    }

}
