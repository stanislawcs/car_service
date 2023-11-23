package com.example.carservice.controllers;

import com.example.carservice.dto.CarDTO;
import com.example.carservice.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAll(){
        return new ResponseEntity<>(carService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getOneById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(carService.getOneById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addOne(@RequestBody @Valid CarDTO carDTO) {
        carService.save(carDTO);
        return new ResponseEntity<>("Car is added", HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id,
                                         @RequestBody @Valid CarDTO carDTO) {
        carService.update(carDTO, id);
        return new ResponseEntity<>("Car is updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        carService.delete(id);
        return new ResponseEntity<>("Car is deleted", HttpStatus.OK);
    }


}
