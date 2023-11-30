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

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private double price;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<TechInspection> techInspections;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Car)) return false;
        final Car other = (Car) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$number = this.getNumber();
        final Object other$number = other.getNumber();
        if (this$number == null ? other$number != null : !this$number.equals(other$number)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Car;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $number = this.getNumber();
        result = result * PRIME + ($number == null ? 43 : $number.hashCode());
        return result;
    }
}
