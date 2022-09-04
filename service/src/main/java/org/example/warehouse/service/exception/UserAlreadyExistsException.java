package org.example.warehouse.service.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UserAlreadyExistsException extends DataIntegrityViolationException {
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
