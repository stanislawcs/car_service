package com.example.carservice.dto;

import com.example.carservice.domain.system.CarBrand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {

    @Valid
    @NotNull
    private CarBrand brand;

    @NotBlank
    @Size(min = 8, max = 8)
    private String number;

    @NotBlank
    private String color;

    @Min(value = 0)
    @Max(value = Integer.MAX_VALUE)
    private double price;
}
