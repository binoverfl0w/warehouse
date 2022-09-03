package org.example.warehouse.service.exception;

public class ResourceNotFoundException extends RuntimeException {
    private final static String msg = "%s with %s %s was not found.";

    public ResourceNotFoundException(String resource, String attribute, String value) {
        super(String.format(msg, resource, attribute, value));
    }
}
