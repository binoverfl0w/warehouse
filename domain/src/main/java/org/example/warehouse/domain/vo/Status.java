package org.example.warehouse.domain.vo;

public class Status {
    private String value;

    public Status(String value) {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("Invalid status");
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
