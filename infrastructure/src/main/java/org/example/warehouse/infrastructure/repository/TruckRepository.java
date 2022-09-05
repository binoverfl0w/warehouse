package org.example.warehouse.infrastructure.repository;

import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.infrastructure.entity.TruckEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<TruckEntity, Long> {
}
