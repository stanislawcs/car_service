package com.example.carservice.dto;


import com.example.carservice.domain.system.CarBrand;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarListDTO {

    private Long id;
    private CarBrand brand;
    private String number;


}
