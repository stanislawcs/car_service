package com.example.carservice.domain;

import com.example.carservice.domain.system.CarBrand;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cars")
@RequiredArgsConstructor
@Getter
@Setter
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    @Enumerated(EnumType.STRING)
    private CarBrand brand;

    @Column(name = "number")
    private String number;

    @Column(name = "color")
    private String color;

    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "car")
    @JsonManagedReference
    private List<TechInspection> techInspections;
}
