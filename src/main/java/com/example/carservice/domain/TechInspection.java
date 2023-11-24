package com.example.carservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tech_inspection")
@ToString
@NoArgsConstructor
public class TechInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat
    @Column(nullable = false)
    private LocalDate dateOfInspection = LocalDate.now();

    @Column(nullable = false)
    private String address;


    private String services;

    @ManyToOne
    @JoinColumn(name = "car_id",
            foreignKey = @ForeignKey(name = "FK_TECH_INSPECTION_CARS"))
    @ToString.Exclude
    private Car car;

    public void setCar(final Car car){
        if(car.getTechInspections() == null)
            car.setTechInspections(new ArrayList<>());

        car.getTechInspections().add(this);
        this.car = car;
    }
}

