package com.example.carservice.dto;

import com.example.carservice.domain.system.CarBrand;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {

    private CarBrand brand;

    private String number;

    private String color;

    private double price;
}
