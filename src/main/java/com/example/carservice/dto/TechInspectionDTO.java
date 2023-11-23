package com.example.carservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechInspectionDTO {

    @JsonProperty(value = "car_id")
    @NotNull
    @Min(value = 1)
    @Max(value = Long.MAX_VALUE)
    private Long carId;

    @NotBlank
    @Pattern(regexp = "^[\\p{L}\\d\\s.,-]+,\\s*[\\p{L}\\d\\s.,-]+,\\s*\\d+$", message = "Address format: city, street, build number")
    private String address;

    @ElementCollection(targetClass = String.class)
    private List<String> services;
}

