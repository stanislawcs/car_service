package com.example.carservice.controllers;

import com.example.carservice.domain.Car;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.mappers.CarMapper;
import com.example.carservice.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;

    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getOneById(@PathVariable("id") Long id){
        return new ResponseEntity<>(carMapper.toDTO(carService.getOneById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addOne(@RequestBody CarDTO carDTO){
        Car car = carMapper.toEntity(carDTO);
        carService.save(car);
        return new ResponseEntity<>("Car is added", HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id,
                                         @RequestBody CarDTO carDTO){
        Car car = carMapper.toEntity(carDTO);
        carService.update(car,id);
        return new ResponseEntity<>("Car is updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        carService.delete(id);
        return new ResponseEntity<>("Car is deleted", HttpStatus.OK);
    }


}
