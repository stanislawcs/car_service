package com.example.carservice.services;

import com.example.carservice.dto.CreationResponse;
import com.example.carservice.dto.TechInspectionDTO;

public interface TechInspectionService {

    TechInspectionDTO getOneById(Long id);

    CreationResponse add(TechInspectionDTO techInspectionDTO, Long carId);
}
