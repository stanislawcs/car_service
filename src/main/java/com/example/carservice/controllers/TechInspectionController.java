package com.example.carservice.controllers;

import com.example.carservice.dto.TechInspectionDTO;
import com.example.carservice.dto.validation.OnCreate;
import com.example.carservice.dto.validation.OnUpdate;
import com.example.carservice.services.TechInspectionService;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> add(@RequestBody @Validated(OnCreate.class) TechInspectionDTO techInspectionDTO) {
        techInspectionService.save(techInspectionDTO);
        return new ResponseEntity<>("Inspection is added", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody @Validated(OnUpdate.class) TechInspectionDTO techInspectionDTO,
                                         @PathVariable("id") Long id) {

        techInspectionService.update(techInspectionDTO, id);
        return new ResponseEntity<>("Inspection is updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        techInspectionService.delete(id);
        return new ResponseEntity<>("Inspection is deleted", HttpStatus.OK);
    }

}
