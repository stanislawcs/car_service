package com.example.carservice.mappers;

import com.example.carservice.domain.Car;
import com.example.carservice.domain.TechInspection;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.dto.CarListDTO;
import com.example.carservice.dto.TechInspectionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = TechInspectionMapper.class)
public interface CarMapper {

    CarDTO toDTO(Car car);

    @Mapping(target = "id", ignore = true)
    Car toEntity(CarDTO dto);

    CarListDTO toListDTO(Car car);

    void toEntity(CarDTO dto, @MappingTarget Car entity);

    List<TechInspectionDTO> techInspectionsToTechInspectionDTOs(List<TechInspection> techInspections, @MappingTarget  List<TechInspectionDTO> techInspectionDTOS);

    List<TechInspection> techInspectionDTOsToTechInspection(List<TechInspectionDTO> techInspectionDTOS, @MappingTarget List<TechInspection> techInspections);


}