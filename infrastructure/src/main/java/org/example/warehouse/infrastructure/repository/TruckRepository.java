package org.example.warehouse.infrastructure.repository;

import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.infrastructure.entity.TruckEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TruckRepository extends JpaRepository<TruckEntity, Long> {
    Set<TruckEntity> findByChassisNumberOrLicensePlate(String chassisNumber, String licensePlate);
}
