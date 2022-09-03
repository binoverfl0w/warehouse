package org.example.warehouse.domain.vo;

public final class Fullname {
    private final String value;

    public Fullname(String value) {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("Invalid fullname");
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
