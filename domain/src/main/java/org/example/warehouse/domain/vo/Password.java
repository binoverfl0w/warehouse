package org.example.warehouse.domain.vo;

public final class Password {
    private final String value;

    public Password(String value) {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("Invalid password");
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
