package org.example.warehouse.domain.vo;

import java.math.BigDecimal;

public final class UnitPrice {
    private BigDecimal value;

    public UnitPrice(BigDecimal value) {
        if (value == null) throw new IllegalArgumentException("Invalid unit price");
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
