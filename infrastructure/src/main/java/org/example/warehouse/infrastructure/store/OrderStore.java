package org.example.warehouse.infrastructure.store;

import org.example.warehouse.domain.order.IOrderStore;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.infrastructure.entity.OrderEntity;
import org.example.warehouse.infrastructure.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderStore implements IOrderStore {
    private OrderRepository orderRepository;

    public OrderStore(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public PageVO<Order> getOrderPage(int page, int size) {
        Page<OrderEntity> orderEntities = orderRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"submittedDate")));
        return new PageVO<>(
                orderEntities.getContent().stream().map(OrderEntity::toDomainOrder).collect(Collectors.toList()),
                orderEntities.getTotalElements(),
                orderEntities.getTotalPages(),
                orderEntities.getNumber()
        );
    }

    @Override
    public PageVO<Order> getOrderPageFilterStatus(Set<String> statuses, int page, int size) {
        Page<OrderEntity> orderEntities = orderRepository.findByStatusIn(statuses, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"submittedDate")));
        return new PageVO<>(
                orderEntities.getContent().stream().map(OrderEntity::toDomainOrder).collect(Collectors.toList()),
                orderEntities.getTotalElements(),
                orderEntities.getTotalPages(),
                orderEntities.getNumber()
        );
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id).map(OrderEntity::toDomainOrder);
    }
}
