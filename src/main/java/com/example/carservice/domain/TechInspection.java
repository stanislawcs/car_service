package com.example.carservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@Table(name = "tech_inspection")
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"dateOfInspection", "address"})
public class TechInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
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

    public void setCar(final Car car) {
        this.car = car;
    }
}

