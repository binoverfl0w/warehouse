package org.example.warehouse.domain.order;

import org.example.warehouse.domain.vo.PageVO;

import java.util.Set;

public interface IOrderStore {
    PageVO<Order> getOrderPage(int page, int size);

    PageVO<Order> getOrderPageFilterStatus(Set<String> statuses, int page, int size);
}
