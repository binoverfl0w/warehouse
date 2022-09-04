package org.example.warehouse.domain.order;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.DomainModel;
import org.example.warehouse.domain.item.Item;
import org.example.warehouse.domain.vo.Quantity;

@Getter
@Setter

public class OrderItem {
    private Item item;
    private Quantity requestedQuantity;

    public OrderItem(Item item, Quantity requestedQuantity) {
        this.item = item;
        this.requestedQuantity = requestedQuantity;
    }

    public void isValid() {
        if (item == null) throw new IllegalArgumentException("Item is required");
        if (requestedQuantity == null) throw new IllegalArgumentException("Requested quantity is required");
    }
}
