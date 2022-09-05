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

    @JsonProperty(value = "user", index = 1)
    private UserDTO userDTO;

    @JsonProperty(value = "submitted_date", index = 2)
    private LocalDateTime submittedDate;

    @JsonProperty(value = "deadline_date", index = 3)
    private LocalDateTime deadlineDate;

    @JsonProperty(value = "status", index = 4)
    private String status;

    @JsonProperty(value = "reason", index = 5)
    private String reason;

    @JsonProperty(value = "delivery_date", index = 6, access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime deliveryDate;

    @JsonProperty(value = "items", index = 7)
    private Set<OrderItemDTO> orderItems;

    public Order toDomainOrder() {
        return new Order(
                id,
                userDTO == null ? null : userDTO.toDomainUser(),
                submittedDate == null ? null : submittedDate,
                deadlineDate == null ? null : deadlineDate,
                status == null ? null : new Status(status),
                reason,
                deliveryDate,
                orderItems == null ? null : orderItems.stream().map(OrderItemDTO::toDomainOrderItem).collect(Collectors.toSet())
        );
    }

    public static OrderDTO fromDomainOrder(Order order) {
        OrderDTO mapOrder = new OrderDTO();
        mapOrder.setId(order.getId());
        mapOrder.setUserDTO(UserDTO.fromDomainUser(order.getUser()));
        mapOrder.setSubmittedDate(order.getSubmittedDate());
        mapOrder.setDeadlineDate(order.getDeadlineDate());
        mapOrder.setStatus(order.getStatus().getValue());
        mapOrder.setReason(order.getReason());
        mapOrder.setDeliveryDate(order.getDeliveryDate());
        mapOrder.setOrderItems(order.getOrderItems().stream().map(OrderItemDTO::fromDomainOrderItem).collect(Collectors.toSet()));
        return mapOrder;
    }
}
