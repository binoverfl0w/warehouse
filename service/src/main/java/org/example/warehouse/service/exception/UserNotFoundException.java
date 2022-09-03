package org.example.warehouse.service.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String attribute, String value) {
        super("User", attribute, value);
    }
}
