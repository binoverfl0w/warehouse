package org.example.warehouse.service.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class TruckAlreadyExistsException extends DataIntegrityViolationException {
    public TruckAlreadyExistsException(String msg) {
        super(msg);
    }
}
