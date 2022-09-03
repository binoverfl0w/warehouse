package org.example.warehouse.infrastructure.repository;

import org.example.warehouse.infrastructure.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
