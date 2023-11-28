package com.example.carservice.services;

import com.example.carservice.dto.CreationResponse;
import com.example.carservice.dto.TechInspectionDTO;

public interface TechInspectionService {

    TechInspectionDTO getOneById(Long id);

    CreationResponse save(TechInspectionDTO techInspectionDTO);

    void update(TechInspectionDTO techInspectionDTO, Long id);

    void delete(Long id);
}
