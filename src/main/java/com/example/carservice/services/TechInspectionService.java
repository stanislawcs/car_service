package com.example.carservice.services;

import com.example.carservice.dto.TechInspectionDTO;

public interface TechInspectionService {

    TechInspectionDTO getOneById(Long id);

    void save(TechInspectionDTO techInspectionDTO);

    void update(TechInspectionDTO techInspectionDTO, Long id);

    void delete(Long id);
}
