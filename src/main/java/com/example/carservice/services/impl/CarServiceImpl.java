package com.example.carservice.services.impl;

import com.example.carservice.domain.Car;
import com.example.carservice.domain.TechInspection;
import com.example.carservice.domain.exceptions.CarNotFoundException;
import com.example.carservice.dto.CreationResponse;
import com.example.carservice.dto.CarDTO;
import com.example.carservice.dto.CarListDTO;
import com.example.carservice.dto.TechInspectionDTO;
import com.example.carservice.dto.converter.Response;
import com.example.carservice.mappers.CarMapper;
import com.example.carservice.mappers.TechInspectionMapper;
import com.example.carservice.repositories.CarRepository;
import com.example.carservice.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CarMapper carMapper;
    private final TechInspectionMapper techInspectionMapper;

    @Value("${api.url}")
    private String url;

    @Override
    public CarDTO getOneById(Long id) throws IOException {
        Car car = carRepository.findById(id).
                orElseThrow(() -> new CarNotFoundException("Car not found"));
        CarDTO carDTO = carMapper.toDTO(car);

        List<TechInspectionDTO> inspectionsDTO = car.getTechInspections().stream().map(techInspectionMapper::toDTO).toList();
        carDTO.setTechInspections(inspectionsDTO);

        getPriceInUSD(carDTO);

        return carDTO;
    }

    @Override
    @Transactional
    public void update(CarDTO carDTO, Long id) {
        Car carToUpdate = carRepository.findById(id).
                orElseThrow(() -> new CarNotFoundException("Car not found"));

        Car car = carMapper.toEntity(carDTO);

        for(int i = 0;i<carDTO.getTechInspections().size();i++){
            TechInspection techInspection = techInspectionMapper.toEntity(carDTO.getTechInspections().get(i),new TechInspection());
        }

//
//        for (TechInspection techInspection : car.getTechInspections()) {
//            techInspection.setCar(carToUpdate);
//            for (int j = 0; j < carToUpdate.getTechInspections().size(); j++) {
//
//                if (techInspection.equals(carToUpdate.getTechInspections().get(j))) {
//
//                    carToUpdate.getTechInspections().get(j).setServices(techInspection.getServices());
//
//                }
//            }
//        }

        carRepository.save(carToUpdate);
    }

    @Override
    @Transactional
    public CreationResponse create(CarDTO carDTO) {
        Car car = connectCarsAndInspections(carDTO);
        carRepository.save(car);

        return new CreationResponse(car.getId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarListDTO> getAll() {
        return carRepository.findAll().stream()
                .map(carMapper::toListDTO).toList();
    }

    private Car connectCarsAndInspections(final CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);

        if (carDTO.getTechInspections() != null) {

            List<TechInspection> inspections = carDTO.getTechInspections().stream()
                    .map(ti -> {
                        TechInspection inspection = techInspectionMapper.toEntity(ti);
                        if (car.getTechInspections() == null)
                            car.setTechInspections(new ArrayList<>());
                        car.getTechInspections().add(inspection);
                        inspection.setCar(car);
                        return inspection;
                    }).toList();

            car.setTechInspections(inspections);
        }

        return car;
    }


    private void getPriceInUSD(CarDTO carDTO) throws IOException {

        String response = restTemplate.getForObject(url, String.class);
        Response usdResponse = objectMapper.readValue(response, Response.class);

        carDTO.setUsdPrice(carDTO.getPrice() / usdResponse.getOfficialRate());
    }

}
