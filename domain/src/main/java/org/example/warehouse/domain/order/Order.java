package org.example.warehouse.domain.order;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.DomainModel;
import org.example.warehouse.domain.vo.Status;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter

public class Order extends DomainModel {
    private LocalDateTime submittedDate;
    private LocalDateTime deadlineDate;
    private Status status;
    private Set<OrderItem> orderItems;

    public Order(Long id, LocalDateTime submittedDate, LocalDateTime deadlineDate, Status status, Set<OrderItem> orderItems) {
        super(id);
        this.submittedDate = submittedDate;
        this.deadlineDate = deadlineDate;
        this.status = status;
        this.orderItems = orderItems;
    }

    public void isValid() {
        if (status == null) throw new IllegalArgumentException("Status is required");
        if (submittedDate == null) throw new IllegalArgumentException("Submitted date is required");
        if (orderItems == null) throw new IllegalArgumentException("Items are required");
    }
}
