package org.example.warehouse.infrastructure.store;

import org.example.warehouse.domain.user.IUserStore;
import org.example.warehouse.domain.user.User;
import org.example.warehouse.infrastructure.entity.UserEntity;
import org.example.warehouse.infrastructure.repository.RoleRepository;
import org.example.warehouse.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserStore implements IUserStore {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public UserStore(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).map(UserEntity::toDomainUser);
    }
}
