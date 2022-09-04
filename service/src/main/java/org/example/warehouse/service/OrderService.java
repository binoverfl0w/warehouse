package org.example.warehouse.service;

import org.example.warehouse.domain.DomainService;
import org.example.warehouse.domain.order.IOrderStore;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.exception.AccessDeniedException;
import org.example.warehouse.service.exception.OrderNotFoundException;

import java.util.Set;

public class OrderService extends DomainService {
    private IOrderStore orderStore;

    public OrderService(IOrderStore orderStore) {
        this.orderStore = orderStore;
    }

    public PageVO<Order> getOrderPage(int page, int size) {
        if (hasRole("WAREHOUSE_MANAGER")) {
            return orderStore.getOrderPage(page, size);
        } else if (hasRole("CLIENT")) {
            return orderStore.getOrderPageForUser(getAuthenticatedUser().getId(), page, size);
        }
        throw new AccessDeniedException();
    }

    public PageVO<Order> getOrderPageFilterStatus(Set<String> statuses, int page, int size) {
        if (hasRole("WAREHOUSE_MANAGER")) {
            return orderStore.getOrderPageFilterStatus(statuses, page, size);
        } else if (hasRole("CLIENT")) {
            return orderStore.getOrderPageFilterStatusForUser(getAuthenticatedUser().getId(), statuses, page, size);
        }
        throw new AccessDeniedException();
    }

    public Order getOrder(Long id) {
        if (hasRole("WAREHOUSE_MANAGER")) {
            return orderStore.findById(id).orElseThrow(() -> new OrderNotFoundException("id", id.toString()));
        } else if (hasRole("CLIENT")) {
            return orderStore.findByIdAndUserId(id, getAuthenticatedUser().getId()).orElseThrow(() -> new OrderNotFoundException("id", id.toString()));
        }
        throw new AccessDeniedException();
    }
}
