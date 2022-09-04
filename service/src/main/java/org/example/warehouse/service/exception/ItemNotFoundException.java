package org.example.warehouse.service.exception;

public class ItemNotFoundException extends ResourceNotFoundException {
    public ItemNotFoundException(String attribute, String value) {
        super("Item", attribute, value);
    }
}
