package org.example.warehouse.service;

import org.example.warehouse.domain.DomainService;
import org.example.warehouse.domain.IAuthenticationFacade;
import org.example.warehouse.domain.user.IUserStore;
import org.example.warehouse.domain.user.User;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.exception.AccessDeniedException;
import org.example.warehouse.service.exception.RoleNotFoundException;
import org.example.warehouse.service.exception.UserNotFoundException;

import java.util.List;

public class UserService extends DomainService {
    private IUserStore userStore;

    public UserService(IUserStore userStore) {
        super();
        this.userStore = userStore;
    }

    public PageVO<User> getUserPage(int page, int size) {
        if (!hasRole("SYSTEM_ADMIN")) throw new AccessDeniedException();
        return userStore.getUserPage(page, size);
    }

    public User getUser(Long id) {
        if (!hasRole("SYSTEM_ADMIN")) throw new AccessDeniedException();
        return userStore.findById(id).orElseThrow(() -> new UserNotFoundException("id", id.toString()));
    }

    public User createUser(User user) {
        if (!hasRole("SYSTEM_ADMIN")) throw new AccessDeniedException();
        user.setRole(userStore.findRoleByName(user.getRole().getName().getValue()).orElseThrow(() -> new RoleNotFoundException("name", user.getRole().getName().getValue())));
        return userStore.save(user);
    }

    public User updateUser(User user) {
        if (!hasRole("SYSTEM_ADMIN")) throw new AccessDeniedException();
        User toUpdate = userStore.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("id", user.getId().toString()));
        if (user.getFullname() != null) toUpdate.setFullname(user.getFullname());
        if (user.getUsername() != null) toUpdate.setUsername(user.getUsername());
        if (user.getEmailAddress() != null) toUpdate.setEmailAddress(user.getEmailAddress());
        if (user.getPassword() != null) toUpdate.setPassword(user.getPassword());
        if (user.getRole() != null) toUpdate.setRole(userStore.findRoleByName(user.getRole().getName().getValue()).orElseThrow(() -> new RoleNotFoundException("name", user.getRole().getName().getValue())));
        toUpdate.isValid();
        return userStore.save(toUpdate);
    }
}
