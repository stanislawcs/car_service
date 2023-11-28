package com.example.carservice.dto;

import com.example.carservice.domain.system.CarBrand;
import com.example.carservice.dto.validation.OnCreate;
import com.example.carservice.dto.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarDTO {

    @Valid
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Brand should not be null")
    private CarBrand brand;

    @NotBlank(message = "Number should not be null")
    @Pattern(regexp = "\\d{4}[A-Z]{2}-[1-7]",groups = {OnCreate.class,OnUpdate.class})
    @Size(min = 8, max = 8, message = "Size should be 8 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String number;

    private String color;

    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private double price;

    private double usdPrice;

    private List<TechInspectionDTO> techInspections;
}
