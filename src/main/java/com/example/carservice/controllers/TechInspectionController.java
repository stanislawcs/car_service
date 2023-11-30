package com.example.carservice.controllers;

import com.example.carservice.dto.CreationResponse;
import com.example.carservice.dto.TechInspectionDTO;
import com.example.carservice.dto.validation.OnCreate;
import com.example.carservice.services.TechInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inspections")
@RequiredArgsConstructor
public class TechInspectionController {

    private final TechInspectionService techInspectionService;

    @GetMapping("/{id}")
    public ResponseEntity<TechInspectionDTO> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(techInspectionService.getOneById(id), HttpStatus.OK);
    }

    @PostMapping("/{carId}")
    public ResponseEntity<CreationResponse> addToCar(@PathVariable("carId") Long carId,
                                                     @RequestBody @Validated(OnCreate.class) TechInspectionDTO techInspectionDTO) {
        return new ResponseEntity<>(techInspectionService.add(techInspectionDTO, carId), HttpStatus.CREATED);
    }

}
