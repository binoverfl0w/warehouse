package org.example.warehouse.application.config;

import org.example.warehouse.application.AuthenticationFacade;
import org.example.warehouse.application.CombinedInterface;
import org.example.warehouse.domain.IAuthenticationFacade;
import org.example.warehouse.domain.item.IItemStore;
import org.example.warehouse.domain.order.IOrderStore;
import org.example.warehouse.domain.truck.ITruckStore;
import org.example.warehouse.domain.user.IUserStore;
import org.example.warehouse.infrastructure.EmailService;
import org.example.warehouse.infrastructure.repository.*;
import org.example.warehouse.infrastructure.store.ItemStore;
import org.example.warehouse.infrastructure.store.OrderStore;
import org.example.warehouse.infrastructure.store.TruckStore;
import org.example.warehouse.infrastructure.store.UserStore;
import org.example.warehouse.service.*;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public IUserStore userStore(UserRepository userRepository, RoleRepository roleRepository) {
        return new UserStore(userRepository, roleRepository);
    }

    @Bean
    public EmailService emailService() { return new EmailService(); }

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

    @Bean
    public ITruckStore truckStore(TruckRepository truckRepository) {
        return new TruckStore(truckRepository);
    }

    @Bean
    public TruckService truckService(ITruckStore truckStore) {
        return new TruckService(truckStore);
    }

    @Bean
    public DeliveryService deliveryService(TruckService truckService, OrderService orderService, ItemService itemService) {
        return new DeliveryService(truckService, orderService, itemService);
    }

    @Bean
    SpringDocConfiguration springDocConfiguration(){
        return new SpringDocConfiguration();
    }
    @Bean
    SpringDocConfigProperties springDocConfigProperties() {
        return new SpringDocConfigProperties();
    }

    @Bean
    ObjectMapperProvider objectMapperProvider(SpringDocConfigProperties springDocConfigProperties){
        return new ObjectMapperProvider(springDocConfigProperties);
    }
}
