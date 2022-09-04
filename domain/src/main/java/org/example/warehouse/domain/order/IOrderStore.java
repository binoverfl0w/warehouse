package org.example.warehouse.domain.order;

import org.example.warehouse.domain.vo.PageVO;

public interface IOrderStore {
    PageVO<Order> getOrderPage(int page, int size);
}
