package org.example.warehouse.application;

import org.example.warehouse.domain.IAuthenticationFacade;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Combined interface so upon instantiation of the class which implements it,
 * a bean will be created for the other interfaces which can injected as needed
 */
public interface CombinedInterface extends IAuthenticationFacade, UserDetailsService {
}
