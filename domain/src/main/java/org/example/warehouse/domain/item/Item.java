package org.example.warehouse.domain.item;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.DomainModel;
import org.example.warehouse.domain.vo.Name;
import org.example.warehouse.domain.vo.Quantity;
import org.example.warehouse.domain.vo.UnitPrice;

@Getter
@Setter

public class Item extends DomainModel {
    private Name name;
    private Quantity quantity;
    private UnitPrice unitPrice;

    public Item(Long id, Name name, Quantity quantity, UnitPrice unitPrice) {
        super(id);
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void isValid() {
        if (name == null) throw new IllegalArgumentException("Name is required");
        if (quantity == null) throw new IllegalArgumentException("Quantity is required");
        if (unitPrice == null) throw new IllegalArgumentException("Unit price is required");
    }
}
