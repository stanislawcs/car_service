package com.example.carservice.domain;

import com.example.carservice.domain.system.CarBrand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "cars")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CarBrand brand;

    @Column(nullable = false,unique = true)
    private String number;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private double price;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<TechInspection> techInspections;
}
