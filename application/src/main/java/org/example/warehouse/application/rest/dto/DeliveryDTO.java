package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter

public class DeliveryDTO {
    @JsonProperty(value = "orderIds", index = 0)
    private Set<Long> orderIds;

    @JsonProperty(value = "truckIds", index = 1)
    private Set<Long> truckIds;

    @JsonProperty(value = "delivery_date", index = 2)
    private LocalDateTime deliveryDate;
}
