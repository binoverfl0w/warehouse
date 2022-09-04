package org.example.warehouse.domain.order;

import org.example.warehouse.domain.vo.PageVO;

import java.util.Optional;
import java.util.Set;

public interface IOrderStore {
    PageVO<Order> getOrderPage(int page, int size);

    PageVO<Order> getOrderPageForUser(Long id, int page, int size);

    PageVO<Order> getOrderPageFilterStatus(Set<String> statuses, int page, int size);

    PageVO<Order> getOrderPageFilterStatusForUser(Long id, Set<String> statuses, int page, int size);

    Optional<Order> findById(Long id);

    Optional<Order> findByIdAndUserId(Long id, Long userId);
}
