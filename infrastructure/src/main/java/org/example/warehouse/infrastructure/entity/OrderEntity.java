package org.example.warehouse.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.vo.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter

@Entity
@Table(name = "`ORDER`")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity user;

    @Column(name = "SUBMITTED_DATE", nullable = false)
    private LocalDateTime submittedDate;

    @Column(name = "DEADLINE_DATE")
    private LocalDateTime deadlineDate;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @OneToMany(mappedBy = "pk.orderEntity")
    private List<OrderItemEntity> orderItems;

    public Order toDomainOrder() {
        return new Order(
                id,
                user.toDomainUser(),
                submittedDate,
                deadlineDate,
                new Status(status),
                orderItems.stream().map(OrderItemEntity::toDomainOrderItem).collect(Collectors.toSet())
        );
    }

    public static OrderEntity fromDomainOrder(Order order) {
        OrderEntity mapOrder = new OrderEntity();
        mapOrder.setUser(UserEntity.fromDomainUser(order.getUser()));
        mapOrder.setSubmittedDate(order.getSubmittedDate());
        mapOrder.setDeadlineDate(order.getDeadlineDate());
        mapOrder.setStatus(order.getStatus().getValue());
        mapOrder.setOrderItems(order.getOrderItems().stream().map(OrderItemEntity::fromDomainOrderItem).collect(Collectors.toList()));
        return mapOrder;
    }
}
