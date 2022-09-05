package org.example.warehouse.service.exception;

public class TruckNotFoundException extends ResourceNotFoundException {
    public TruckNotFoundException(String attribute, String value) {
        super("Truck", attribute, value);
    }
}
