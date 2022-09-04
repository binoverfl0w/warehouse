package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.vo.Status;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter

public class OrderDTO {
    @JsonProperty(value = "id", index = 0)
    private Long id;

    @JsonProperty(value = "submitted_date", index = 1)
    private LocalDateTime submittedDate;

    @JsonProperty(value = "deadline_date", index = 2)
    private LocalDateTime deadlineDate;

    @JsonProperty(value = "status", index = 3)
    private String status;

    @JsonProperty(value = "items", index = 4)
    private Set<OrderItemDTO> orderItems;

    public Order toDomainOrder() {
        return new Order(
                id,
                submittedDate == null ? null : submittedDate,
                deadlineDate == null ? null : deadlineDate,
                status == null ? null : new Status(status),
                orderItems == null ? null : orderItems.stream().map(OrderItemDTO::toDomainOrderItem).collect(Collectors.toSet())
        );
    }

    public static OrderDTO fromDomainOrder(Order order) {
        OrderDTO mapOrder = new OrderDTO();
        mapOrder.setId(order.getId());
        mapOrder.setSubmittedDate(order.getSubmittedDate());
        mapOrder.setDeadlineDate(order.getDeadlineDate());
        mapOrder.setStatus(order.getStatus().getValue());
        mapOrder.setOrderItems(order.getOrderItems().stream().map(OrderItemDTO::fromDomainOrderItem).collect(Collectors.toSet()));
        return mapOrder;
    }
}
