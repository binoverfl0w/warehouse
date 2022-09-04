package org.example.warehouse.infrastructure.repository;

import org.example.warehouse.domain.order.Order;
import org.example.warehouse.infrastructure.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findByStatusIn(Set<String> statuses, Pageable pageable);

    Optional<OrderEntity> findByIdAndUserId(Long id, Long userId);

    Page<OrderEntity> findByUserId(Long id, Pageable pageable);

    Page<OrderEntity> findByUserIdAndStatusIn(Long id, Set<String> statuses, Pageable pageable);
}
