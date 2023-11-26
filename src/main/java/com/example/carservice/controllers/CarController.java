package com.example.carservice.controllers;

import com.example.carservice.dto.CarCreationResponse;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.dto.CarListDTO;
import com.example.carservice.dto.validation.OnCreate;
import com.example.carservice.dto.validation.OnUpdate;
import com.example.carservice.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;


    @GetMapping
    public ResponseEntity<List<CarListDTO>> getAll(){
        return new ResponseEntity<>(carService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getOneById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(carService.getOneById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarCreationResponse> create(@RequestBody @Validated(OnCreate.class) CarDTO carDTO) {
        return new ResponseEntity<>(carService.create(carDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id,
                                         @RequestBody @Validated(OnUpdate.class) CarDTO carDTO) {
        carService.update(carDTO, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        carService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
