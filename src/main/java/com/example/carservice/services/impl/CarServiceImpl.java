package com.example.carservice.services.impl;

import com.example.carservice.domain.Car;
import com.example.carservice.domain.TechInspection;
import com.example.carservice.domain.exceptions.CarNotFoundException;
import com.example.carservice.dto.CarCreationResponse;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.dto.CarListDTO;
import com.example.carservice.dto.TechInspectionDTO;
import com.example.carservice.dto.converter.Response;
import com.example.carservice.mappers.CarMapper;
import com.example.carservice.mappers.TechInspectionMapper;
import com.example.carservice.repositories.CarRepository;
import com.example.carservice.services.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    @Override
    public CarDTO getOneById(Long id) throws IOException {
        Car car = carRepository.findById(id).
                orElseThrow(() -> new CarNotFoundException("Car not found"));
        CarDTO carDTO = CarMapper.INSTANCE.toDTO(car);

        List<TechInspectionDTO> inspectionsDTO = car.getTechInspections().stream().map(TechInspectionMapper.INSTANCE::toDTO).toList();
        carDTO.setTechInspections(inspectionsDTO);

        getPriceInUSD(carDTO);

        return carDTO;
    }

    @Override
    @Transactional
    public void update(CarDTO carDTO, Long id) {
        Car car = connectCarsAndInspections(carDTO);
        car.setId(id);
        carRepository.save(car);
    }

    @Override
    @Transactional
    public CarCreationResponse create(CarDTO carDTO) {
        Car car = connectCarsAndInspections(carDTO);
        carRepository.save(car);

        CarCreationResponse response = new CarCreationResponse();
        response.setId(car.getId());
        return response;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarListDTO> getAll() {
        return carRepository.findAll().stream()
                .map(CarMapper.INSTANCE::toListDTO).toList();
    }

    private Car connectCarsAndInspections(final CarDTO carDTO) {
        Car car = CarMapper.INSTANCE.toEntity(carDTO);

        if (carDTO.getTechInspections() != null) {

            List<TechInspection> inspections = carDTO.getTechInspections().stream()
                    .map(ti -> {
                        TechInspection inspection = TechInspectionMapper.INSTANCE.toEntity(ti);
                        inspection.setCar(car);
                        return inspection;
                    }).toList();

            car.setTechInspections(inspections);
        }

        return car;
    }

    private void getPriceInUSD(CarDTO carDTO) throws IOException {
        String url = "http://localhost:8080/converter/get-rate?first-currency=USD&second-currency=BYN";

        String response = restTemplate.getForObject(url, String.class);
        Response usdResponse = objectMapper.readValue(response, Response.class);


        carDTO.setUsdPrice(carDTO.getPrice()/usdResponse.getOfficialRate());
    }

}
