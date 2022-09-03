package org.example.warehouse.service.exception;

public class RoleNotFoundException extends ResourceNotFoundException
{
    public RoleNotFoundException(String attribute, String value) {
        super("Role", attribute, value);
    }
}
