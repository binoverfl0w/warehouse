package org.example.warehouse.domain.vo;

public final class Username {
    private final String value;

    public Username(String value) {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("Invalid username");
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
