package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.vo.Status;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Getter
@Setter

public class OrderBasicDTO {
    @JsonProperty(value = "id", index = 0)
    private Long id;

    @JsonProperty(value = "submitted_date", index = 1)
    private LocalDateTime submittedDate;

    @JsonProperty(value = "deadline_date", index = 2)
    private LocalDateTime deadlineDate;

    @JsonProperty(value = "status", index = 3)
    private String status;

    public static OrderBasicDTO fromDomainOrder(Order order) {
        OrderBasicDTO mapOrderBasic = new OrderBasicDTO();
        mapOrderBasic.setId(order.getId());
        mapOrderBasic.setSubmittedDate(order.getSubmittedDate());
        mapOrderBasic.setDeadlineDate(order.getDeadlineDate());
        mapOrderBasic.setStatus(order.getStatus().getValue());
        return mapOrderBasic;
    }
}
