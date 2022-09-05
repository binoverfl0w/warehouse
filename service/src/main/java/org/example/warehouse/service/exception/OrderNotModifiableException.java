package org.example.warehouse.service.exception;

public class OrderNotModifiableException extends RuntimeException {
    public OrderNotModifiableException(String msg) {
        super(msg);
    }

    public OrderNotModifiableException() {}
}
