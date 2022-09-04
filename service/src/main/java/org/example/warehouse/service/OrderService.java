package org.example.warehouse.service;

import org.example.warehouse.domain.DomainService;
import org.example.warehouse.domain.order.IOrderStore;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.exception.AccessDeniedException;

public class OrderService extends DomainService {
    private IOrderStore orderStore;

    public OrderService(IOrderStore orderStore) {
        this.orderStore = orderStore;
    }

    public PageVO<Order> getOrderPage(int page, int size) {
        if (!hasRole("WAREHOUSE_MANAGER") && !hasRole("CLIENT")) throw new AccessDeniedException();
        return orderStore.getOrderPage(page, size);
    }
}
