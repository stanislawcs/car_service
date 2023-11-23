package com.example.carservice.mappers;

import com.example.carservice.domain.TechInspection;
import com.example.carservice.dto.TechInspectionDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class TechInspectionMapper implements Mapper<TechInspection, TechInspectionDTO>{

    private final ModelMapper modelMapper;

    @Override
    public TechInspection toEntity(TechInspectionDTO dto) {
        return modelMapper.map(dto,TechInspection.class);
    }

    @Override
    public TechInspectionDTO toDTO(TechInspection entity) {
        TechInspectionDTO dto = modelMapper.map(entity,TechInspectionDTO.class);
        dto.setServices(Arrays.asList(entity.getServices().split(",")));
        return dto;
    }
}
