package com.example.carservice.dto;

import com.example.carservice.dto.validation.OnCreate;
import com.example.carservice.dto.validation.OnUpdate;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechInspectionDTO {

    @NotNull(message = "Car Id should not be null",
            groups = OnCreate.class)
    private Long carId;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "^[\\p{L}\\d\\s.,-]+,\\s*[\\p{L}\\d\\s.,-]+,\\s*\\d+$",
            message = "Address format: city, street, build number", groups = {OnCreate.class, OnUpdate.class})
    private String address;

    @ElementCollection(targetClass = String.class)
    private List<String> services;
}

