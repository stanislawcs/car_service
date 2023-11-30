package com.example.carservice.services.impl;

import com.example.carservice.domain.Car;
import com.example.carservice.domain.TechInspection;
import com.example.carservice.domain.exceptions.CarNotFoundException;
import com.example.carservice.domain.exceptions.InspectionNotFoundException;
import com.example.carservice.dto.CreationResponse;
import com.example.carservice.dto.TechInspectionDTO;
import com.example.carservice.mappers.TechInspectionMapper;
import com.example.carservice.repositories.CarRepository;
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
    private final TechInspectionMapper techInspectionMapper;
    private final CarRepository carRepository;

    @Override
    public TechInspectionDTO getOneById(Long id) {
        return techInspectionMapper.toDTO(techInspectionRepository.
                findById(id).orElseThrow(() -> new InspectionNotFoundException("Inspection not found")));
    }

    @Override
    @Transactional
    public CreationResponse add(TechInspectionDTO techInspectionDTO, Long carId) {
        TechInspection techInspection = techInspectionMapper.toEntity(techInspectionDTO);
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car not found!"));
        car.getTechInspections().add(techInspection);
        techInspection.setCar(car);
        techInspectionRepository.save(techInspection);
        return new CreationResponse(techInspection.getId());
    }


}
