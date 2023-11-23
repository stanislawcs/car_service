package com.example.carservice.services.impl;

import com.example.carservice.domain.TechInspection;
import com.example.carservice.domain.exceptions.InspectionNotFoundException;
import com.example.carservice.dto.TechInspectionDTO;
import com.example.carservice.mappers.Mapper;
import com.example.carservice.repositories.TechInspectionRepository;
import com.example.carservice.services.TechInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TechInspectionServiceImpl implements TechInspectionService {

    private final TechInspectionRepository techInspectionRepository;
    private final Mapper<TechInspection, TechInspectionDTO> mapper;

    @Override
    public TechInspectionDTO getOneById(Long id) {
        return mapper.toDTO(techInspectionRepository.
                findById(id).orElseThrow(()-> new InspectionNotFoundException("Inspection not found")));
    }

    @Override
    @Transactional
    public void save(TechInspectionDTO techInspectionDTO) {
        TechInspection techInspection = mapper.toEntity(techInspectionDTO);
        techInspectionRepository.save(techInspection);
    }

    @Override
    @Transactional
    public void update(TechInspectionDTO techInspectionDTO, Long id) {
        TechInspection techInspection = mapper.toEntity(techInspectionDTO);
        techInspection.setId(id);
        techInspectionRepository.save(techInspection);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        techInspectionRepository.deleteById(id);
    }

}
