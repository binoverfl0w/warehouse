package org.example.warehouse.domain.vo;

import java.io.Serializable;

public final class Description {
    private final String value;

    public Description(String value) {
        if (value.isEmpty()) value = null;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
