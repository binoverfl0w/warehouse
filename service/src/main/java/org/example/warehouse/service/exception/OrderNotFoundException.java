package org.example.warehouse.service.exception;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(String attribute, String value) {
        super("Order", attribute, value);
    }
}
