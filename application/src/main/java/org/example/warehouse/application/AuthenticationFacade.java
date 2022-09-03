package org.example.warehouse.application;

import org.example.warehouse.application.rest.dto.UserDTO;
import org.example.warehouse.domain.user.IUserStore;
import org.example.warehouse.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthenticationFacade implements CombinedInterface {
    private IUserStore userStore;

    public AuthenticationFacade(IUserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public User getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO authenticated = (UserDTO) authentication.getPrincipal();
        return authenticated == null ? null : authenticated.toDomainUser();
    }

    @Override
    public boolean hasRole(String role) {
        User authenticated = getAuthenticatedUser();
        if (authenticated == null) return false;
        return authenticated.getRole().getName().getValue().equals(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDTO.fromDomainUser(userStore.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User with username %s was not found.", username))));
    }
}
