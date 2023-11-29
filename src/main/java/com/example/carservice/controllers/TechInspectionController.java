package com.example.carservice.controllers;

import com.example.carservice.dto.CreationResponse;
import com.example.carservice.dto.TechInspectionDTO;
import com.example.carservice.dto.validation.OnCreate;
import com.example.carservice.dto.validation.OnUpdate;
import com.example.carservice.services.TechInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inspection")
@RequiredArgsConstructor
public class TechInspectionController {

    private final TechInspectionService techInspectionService;

    @GetMapping("/{id}")
    public ResponseEntity<TechInspectionDTO> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(techInspectionService.getOneById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreationResponse> add(@RequestBody @Validated(OnCreate.class) TechInspectionDTO techInspectionDTO) {

        return new ResponseEntity<>(techInspectionService.save(techInspectionDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{carId}")
    public ResponseEntity<CreationResponse> addToCar(@PathVariable String carId,
                                                     @RequestBody @Validated(OnCreate.class) TechInspectionDTO techInspectionDTO) {

        return new ResponseEntity<>(techInspectionService.save(techInspectionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Validated(OnUpdate.class) TechInspectionDTO techInspectionDTO,
                                             @PathVariable("id") Long id) {

        techInspectionService.update(techInspectionDTO, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        techInspectionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
