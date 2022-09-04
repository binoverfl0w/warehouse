package org.example.warehouse.infrastructure.store;

import org.example.warehouse.domain.order.IOrderStore;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.infrastructure.entity.OrderEntity;
import org.example.warehouse.infrastructure.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;

public class OrderStore implements IOrderStore {
    private OrderRepository orderRepository;

    public OrderStore(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public PageVO<Order> getOrderPage(int page, int size) {
        Page<OrderEntity> orderEntities = orderRepository.findAll(PageRequest.of(page, size));
        return new PageVO<>(
                orderEntities.getContent().stream().map(OrderEntity::toDomainOrder).collect(Collectors.toList()),
                orderEntities.getTotalElements(),
                orderEntities.getTotalPages(),
                orderEntities.getNumber()
        );
    }
}
