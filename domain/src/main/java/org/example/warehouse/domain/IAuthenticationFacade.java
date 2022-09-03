package org.example.warehouse.domain;

import org.example.warehouse.domain.user.User;

public interface IAuthenticationFacade {
    User getAuthenticatedUser();
    boolean hasRole(String role);
}