package com.example.carservice.repositories;

import com.example.carservice.domain.TechInspection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechInspectionRepository extends JpaRepository<TechInspection,Long> {
}
