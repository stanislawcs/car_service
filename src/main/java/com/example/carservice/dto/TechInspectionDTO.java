package com.example.carservice.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechInspectionDTO {

    @NotBlank
    @Pattern(regexp = "^[\\p{L}\\d\\s.,-]+,\\s*[\\p{L}\\d\\s.,-]+,\\s*\\d+$", message = "Address format: city, street, build number")
    private String address;

    @ElementCollection(targetClass = String.class)
    private List<String> services;
}

