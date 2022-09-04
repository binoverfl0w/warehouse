package org.example.warehouse.infrastructure.repository;

import org.example.warehouse.infrastructure.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
