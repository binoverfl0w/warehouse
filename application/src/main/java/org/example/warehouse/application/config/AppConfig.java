package org.example.warehouse.application.config;

import org.example.warehouse.application.AuthenticationFacade;
import org.example.warehouse.application.CombinedInterface;
import org.example.warehouse.domain.IAuthenticationFacade;
import org.example.warehouse.domain.item.IItemStore;
import org.example.warehouse.domain.order.IOrderStore;
import org.example.warehouse.domain.user.IUserStore;
import org.example.warehouse.infrastructure.repository.ItemRepository;
import org.example.warehouse.infrastructure.repository.OrderRepository;
import org.example.warehouse.infrastructure.repository.RoleRepository;
import org.example.warehouse.infrastructure.repository.UserRepository;
import org.example.warehouse.infrastructure.store.ItemStore;
import org.example.warehouse.infrastructure.store.OrderStore;
import org.example.warehouse.infrastructure.store.UserStore;
import org.example.warehouse.service.ItemService;
import org.example.warehouse.service.OrderService;
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

    @Bean
    public IItemStore itemStore(ItemRepository itemRepository) {
        return new ItemStore(itemRepository);
    }

    @Bean
    public ItemService itemService(IItemStore itemStore) {
        return new ItemService(itemStore);
    }

    @Bean
    public IOrderStore orderStore(OrderRepository orderRepository) {
        return new OrderStore(orderRepository);
    }

    @Bean
    public OrderService orderService(IOrderStore orderStore) {
        return new OrderService(orderStore);
    }
}
