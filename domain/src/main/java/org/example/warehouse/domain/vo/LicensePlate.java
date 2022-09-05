package org.example.warehouse.domain.vo;

public final class LicensePlate {
    private String value;

    public LicensePlate(String value) {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("Invalid license plate");
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
