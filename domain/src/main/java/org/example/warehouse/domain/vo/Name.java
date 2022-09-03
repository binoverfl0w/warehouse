package org.example.warehouse.domain.vo;

import java.io.Serializable;

public final class Name {
    private final String value;

    public Name(String value) {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("Invalid name");
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
