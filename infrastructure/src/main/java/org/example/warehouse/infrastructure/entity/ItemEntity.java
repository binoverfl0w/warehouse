package org.example.warehouse.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.item.Item;
import org.example.warehouse.domain.vo.Name;
import org.example.warehouse.domain.vo.Quantity;
import org.example.warehouse.domain.vo.UnitPrice;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter

@Entity
@Table(name = "ITEM")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ITEM_NAME", nullable = false)
    private String itemName;

    @Column(name = "QUANTITY", nullable = false)
    private Long quantity;

    @Column(name = "UNIT_PRICE", nullable = false)
    private BigDecimal unitPrice;

    public Item toDomainItem() {
        return new Item(
                id,
                new Name(itemName),
                new Quantity(quantity),
                new UnitPrice(unitPrice)
        );
    }

    public ItemEntity fromDomainItem(Item item) {
        ItemEntity mapItem = new ItemEntity();
        mapItem.setId(item.getId());
        mapItem.setItemName(item.getName().getValue());
        mapItem.setQuantity(item.getQuantity().getValue());
        mapItem.setUnitPrice(item.getUnitPrice().getValue());
        return mapItem;
    }
}
