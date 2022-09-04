package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.item.Item;
import org.example.warehouse.domain.vo.Name;
import org.example.warehouse.domain.vo.Quantity;
import org.example.warehouse.domain.vo.UnitPrice;

import java.math.BigDecimal;

@Getter
@Setter

public class ItemDTO {
    @JsonProperty(value = "id", index = 0)
    private Long id;

    @JsonProperty(value = "item_name", index = 1)
    private String name;

    @JsonProperty(value = "quantity", index = 2)
    private Long quantity;

    @JsonProperty(value = "unit_price", index = 3)
    private BigDecimal unitPrice;

    public Item toDomainItem() {
        return new Item(
                id,
                name == null ? null : new Name(name),
                quantity == null ? null : new Quantity(quantity),
                unitPrice == null ? null : new UnitPrice(unitPrice)
        );
    }

    public static ItemDTO fromDomainItem(Item item) {
        ItemDTO mapItem = new ItemDTO();
        mapItem.setId(item.getId());
        mapItem.setName(item.getName().getValue());
        mapItem.setQuantity(item.getQuantity().getValue());
        mapItem.setUnitPrice(item.getUnitPrice().getValue());
        return mapItem;
    }
}
