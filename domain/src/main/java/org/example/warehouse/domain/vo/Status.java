package org.example.warehouse.domain.vo;

import java.util.Arrays;

public final class Status {
    private String value;
    public enum VALUES {
        CREATED,
        AWAITING_APPROVAL,
        APPROVED,
        DECLINED,
        UNDER_DELIVERY,
        FULFILLED,
        CANCELLED
    }
    public Status(String value) {
        if (value == null || value.isEmpty() || Arrays.stream(VALUES.values()).filter(v -> v.name().equals(value)).findFirst().isEmpty()) throw new IllegalArgumentException("Invalid status");
        this.value = value;
    }

    public Status(VALUES val) {
        this.value = val.name();
    }

    public String getValue() {
        return value;
    }
}
