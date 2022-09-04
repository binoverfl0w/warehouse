package org.example.warehouse.domain.vo;

public class Quantity {
    private Long value;

    public Quantity(Long value) {
        if (value == null) throw new IllegalArgumentException("Invalid quantity");
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
