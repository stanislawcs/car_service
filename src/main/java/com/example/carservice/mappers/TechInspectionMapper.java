package com.example.carservice.mappers;

import com.example.carservice.domain.TechInspection;
import com.example.carservice.dto.TechInspectionDTO;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TechInspectionMapper {

    TechInspectionDTO toDTO(TechInspection techInspection);

    TechInspection toEntity(TechInspectionDTO dto);

    default List<String> servicesFromString(String services) {
        return Arrays.asList(services.split(","));
    }

    default String servicesToString(List<String> services) {
        return String.join(",", services);
    }


}
