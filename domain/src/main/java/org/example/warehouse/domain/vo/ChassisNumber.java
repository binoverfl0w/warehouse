package org.example.warehouse.domain.vo;

public final class ChassisNumber {
    private String value;

    public ChassisNumber(String value) {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("Invalid chassis number");
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
