package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.order.OrderItem;
import org.example.warehouse.domain.vo.Quantity;

@Getter
@Setter

public class OrderItemDTO {
    @JsonProperty(value = "item", index = 0)
    private ItemDTO itemDTO;

    @JsonProperty(value = "requested_quantity", index = 1)
    private Long quantity;

    public OrderItem toDomainOrderItem() {
        return new OrderItem(
                itemDTO.toDomainItem(),
                new Quantity(quantity)
        );
    }

    public static OrderItemDTO fromDomainOrderItem(OrderItem orderItem) {
        OrderItemDTO mapOrderItem = new OrderItemDTO();
        mapOrderItem.setItemDTO(ItemDTO.fromDomainItem(orderItem.getItem()));
        mapOrderItem.setQuantity(orderItem.getRequestedQuantity().getValue());
        return mapOrderItem;
    }
}
