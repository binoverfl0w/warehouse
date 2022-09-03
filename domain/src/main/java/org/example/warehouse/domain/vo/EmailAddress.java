package org.example.warehouse.domain.vo;

import java.util.regex.Pattern;

public final class EmailAddress {
    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    private final String value;

    public EmailAddress(String value) {
        if (!isValid(value)) throw new IllegalArgumentException("Invalid email address");
        this.value = value;
    }

    public static boolean isValid(String value) {
        if (value == null || value.isEmpty()) return false;
        return PATTERN.matcher(value).matches();
    }

    public String getValue() {
        return value;
    }
}
