package org.example.warehouse.infrastructure.store;

import org.example.warehouse.domain.user.IUserStore;
import org.example.warehouse.domain.user.User;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.infrastructure.entity.UserEntity;
import org.example.warehouse.infrastructure.repository.RoleRepository;
import org.example.warehouse.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public PageVO<User> getUserPage(int page, int size) {
        Page<UserEntity> userEntities = userRepository.findAll(PageRequest.of(page, size));
        return new PageVO<>(
                userEntities.getContent().stream().map(UserEntity::toDomainUser).collect(Collectors.toList()),
                userEntities.getTotalElements(),
                userEntities.getTotalPages(),
                userEntities.getNumber()
        );
    }
}
