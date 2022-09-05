package org.example.warehouse.infrastructure.store;

import org.example.warehouse.domain.order.IOrderStore;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.order.OrderItem;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.infrastructure.entity.ItemEntity;
import org.example.warehouse.infrastructure.entity.OrderEntity;
import org.example.warehouse.infrastructure.entity.OrderItemEntity;
import org.example.warehouse.infrastructure.entity.UserEntity;
import org.example.warehouse.infrastructure.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.*;
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
    public PageVO<Order> getOrderPageForUser(Long id, int page, int size) {
        Page<OrderEntity> orderEntities = orderRepository.findByUserId(id, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"submittedDate")));
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
    public PageVO<Order> getOrderPageFilterStatusForUser(Long id, Set<String> statuses, int page, int size) {
        Page<OrderEntity> orderEntities = orderRepository.findByUserIdAndStatusIn(id, statuses, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"submittedDate")));
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

    @Override
    public Optional<Order> findByIdAndUserId(Long id, Long userId) {
        return orderRepository.findByIdAndUserId(id, userId).map(OrderEntity::toDomainOrder);
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = order.getId() == null ? null : orderRepository.findById(order.getId()).orElse(null);
        if (orderEntity == null) {
            orderEntity = new OrderEntity();
            orderEntity.setId(order.getId());
            orderEntity.setUser(UserEntity.fromDomainUser(order.getUser()));
            orderEntity.setSubmittedDate(order.getSubmittedDate());
            orderEntity.setDeadlineDate(order.getDeadlineDate());
            orderEntity.setStatus(order.getStatus().getValue());
        }
        if (orderEntity.getOrderItems() != null) orderEntity.getOrderItems().clear();
        else orderEntity.setOrderItems(new HashSet<>());
        for (OrderItem orderItem : order.getOrderItems()) {
            OrderItemEntity orderItemEntity = OrderItemEntity.fromDomainOrderItem(orderItem);
            orderItemEntity.setOrderEntity(orderEntity);
            orderEntity.getOrderItems().add(orderItemEntity);
        }
        return orderRepository.save(orderEntity).toDomainOrder();
    }
}
