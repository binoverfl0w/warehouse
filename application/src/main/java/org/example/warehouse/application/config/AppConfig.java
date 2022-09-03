package org.example.warehouse.application.config;

import org.example.warehouse.application.AuthenticationFacade;
import org.example.warehouse.application.CombinedInterface;
import org.example.warehouse.domain.IAuthenticationFacade;
import org.example.warehouse.domain.user.IUserStore;
import org.example.warehouse.infrastructure.repository.RoleRepository;
import org.example.warehouse.infrastructure.repository.UserRepository;
import org.example.warehouse.infrastructure.store.UserStore;
import org.example.warehouse.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public IUserStore userStore(UserRepository userRepository, RoleRepository roleRepository) {
        return new UserStore(userRepository, roleRepository);
    }

    @Bean
    public CombinedInterface authenticationFacade(IUserStore userStore) {
        return new AuthenticationFacade(userStore);
    }

    @Bean
    public UserService userService(IUserStore userStore) {
        return new UserService(userStore);
    }
}
