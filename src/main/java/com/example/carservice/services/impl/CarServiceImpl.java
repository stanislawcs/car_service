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
import com.example.carservice.services.TechInspectionService;
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

    private final TechInspectionService techInspectionService;
    private final CarRepository carRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.url}")
    private String url;

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
        Car carToUpdate = carRepository.findById(id).
                orElseThrow(()->new CarNotFoundException("Car not found"));

        carToUpdate.setBrand(carDTO.getBrand());
        carToUpdate.setNumber(carDTO.getNumber());
        carToUpdate.setColor(carDTO.getColor());
        carToUpdate.setPrice(carDTO.getPrice());

        List<TechInspectionDTO> inspectionsDTO = carDTO.getTechInspections().stream().map(x->{
            x.setCarId(id);
            return x;}).toList();

        // то есть мы должны связать дтошные то и те которые есть в бд, и пройтись по двум спискам найдя разницу
        List<TechInspection> techInspections = CarMapper.INSTANCE.toEntity(carDTO).getTechInspections();

        for (TechInspection techInspection : techInspections) {
            techInspection.setCar(carToUpdate);
            for (int j = 0; j < carToUpdate.getTechInspections().size(); j++) {

                if(techInspection.getAddress().equals(carToUpdate.getTechInspections().get(j).getAddress())&&
                        techInspection.getDateOfInspection().toString().equals(carToUpdate.getTechInspections().get(j).getDateOfInspection().toString())) {
                    carToUpdate.getTechInspections().get(j).setServices(techInspection.getServices());

                }
            }
        }


        carRepository.save(carToUpdate);
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

//    private Car connectCarAndInspectionsForUpdate(final CarDTO carDTO) {
//        Car car = CarMapper.INSTANCE.toEntity(carDTO);
//
//        if (carDTO.getTechInspections() != null) {
//            List<TechInspection> inspections = carDTO.getTechInspections().stream()
//                    .map(tiDTO -> {
//                        TechInspection inspection = TechInspectionMapper.INSTANCE.toEntity(tiDTO);
//                        car.setTechInspections(new ArrayList<>());
//                        inspection.setCar(car);
//                        return inspection;
//                    }).toList();
//
//            car.setTechInspections(inspections);
//        }
//
//        return car;
//    }

    private void getPriceInUSD(CarDTO carDTO) throws IOException {

        String response = restTemplate.getForObject(url, String.class);
        Response usdResponse = objectMapper.readValue(response, Response.class);

        carDTO.setUsdPrice(carDTO.getPrice() / usdResponse.getOfficialRate());
    }

}
