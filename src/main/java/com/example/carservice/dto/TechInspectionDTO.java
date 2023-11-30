package com.example.carservice.dto;

import com.example.carservice.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TechInspectionDTO {

    private Long id;

    @NotNull(groups = OnCreate.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfInspection;

    @NotBlank(groups = OnCreate.class)
    @Pattern(regexp = "^[\\p{L}\\d\\s.,-]+,\\s*[\\p{L}\\d\\s.,-]+,\\s*\\d+$",
            message = "Address format: city, street, build number", groups = OnCreate.class)
    private String address;

    @ElementCollection(targetClass = String.class)
    private List<String> services;
}

