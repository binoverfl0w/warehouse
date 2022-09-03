package org.example.warehouse.domain;

import org.example.warehouse.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DomainService {
    @Autowired private IAuthenticationFacade authenticationFacade;

    public User getAuthenticatedUser() {
        return authenticationFacade.getAuthenticatedUser();
    }

    public boolean hasRole(String role) {
        return authenticationFacade.hasRole(role);
    }
}
