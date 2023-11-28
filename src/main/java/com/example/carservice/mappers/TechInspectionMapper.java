package com.example.carservice.mappers;

import com.example.carservice.domain.TechInspection;
import com.example.carservice.dto.TechInspectionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TechInspectionMapper {

    @Mapping(target = "carId", source = "car.id")
    TechInspectionDTO toDTO(TechInspection techInspection);

    @Mapping(target = "car.id", source = "carId")
    TechInspection toEntity(TechInspectionDTO dto);

    @Mapping(target = "car.id", source = "carId")
    @Mapping(target = "address", qualifiedByName = "update")
    TechInspection toEntity(TechInspectionDTO dto, @MappingTarget TechInspection entity);


    default List<String> servicesFromString(String services) {
        return Arrays.asList(services.split(","));
    }

    default String servicesToString(List<String> services) {
        return String.join(",", services);
    }

    @Named("update")
    default List<TechInspection> updateTechInspections(List<TechInspectionDTO> carDTOTechInspections, List<TechInspection> techInspectionsFromDB) {

        List<TechInspection> carTechInspections = carDTOTechInspections.stream().map(this::toEntity).toList();

        List<TechInspection> updatedTechInspections = new ArrayList<>();

        for (TechInspection carTechInspection : carTechInspections) {
            TechInspection existedTechInspection = findExistingInspection(techInspectionsFromDB, carTechInspection);
            if (existedTechInspection != null) {
                updatedTechInspections.add(existedTechInspection);
            } else {
                updatedTechInspections.add(carTechInspection);
            }
        }
        return updatedTechInspections;
    }

    default TechInspection findExistingInspection(List<TechInspection> inspections, TechInspection inspectionFromDTO) {
        return inspections.stream()
                .filter(inspection -> inspection.equals(inspectionFromDTO))
                .findFirst()
                .orElse(null);
    }
}
