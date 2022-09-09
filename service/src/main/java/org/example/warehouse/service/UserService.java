package org.example.warehouse.service;

import org.example.warehouse.domain.DomainService;
import org.example.warehouse.domain.IAuthenticationFacade;
import org.example.warehouse.domain.user.IUserStore;
import org.example.warehouse.domain.user.User;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.domain.vo.Password;
import org.example.warehouse.domain.vo.ResetToken;
import org.example.warehouse.service.exception.AccessDeniedException;
import org.example.warehouse.service.exception.RoleNotFoundException;
import org.example.warehouse.service.exception.UserAlreadyExistsException;
import org.example.warehouse.service.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        if (hasRole("SYSTEM_ADMIN")) {
            user.setRole(userStore.findRoleByName(user.getRole().getName().getValue()).orElseThrow(() -> new RoleNotFoundException("name", user.getRole().getName().getValue())));
        } else {
            user.setRole(userStore.findRoleByName("CLIENT").orElseThrow(() -> new RoleNotFoundException("name", "CLIENT")));
        }
        user.isValid();
        userStore.findByUsername(user.getUsername().getValue()).ifPresent((presentUser) -> {throw new UserAlreadyExistsException("A user with this username already exists");});
        userStore.findByEmail(user.getEmailAddress().getValue()).ifPresent((presentUser) -> {throw new UserAlreadyExistsException("A user with this email already exists");});
        return userStore.save(user);
    }

    public User updateUser(User user) {
        if (!hasRole("SYSTEM_ADMIN")) throw new AccessDeniedException();
        User toUpdate = userStore.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("id", user.getId().toString()));
        if (user.getFullname() != null) toUpdate.setFullname(user.getFullname());
        if (user.getUsername() != null) {
            userStore.findByUsername(user.getUsername().getValue()).ifPresent((presentUser) -> {
                if (presentUser.getId() != user.getId())
                    throw new UserAlreadyExistsException("A user with this username already exists");
            });
            toUpdate.setUsername(user.getUsername());
        }
        if (user.getEmailAddress() != null) {
            userStore.findByEmail(user.getUsername().getValue()).ifPresent((presentUser) -> {
                if (presentUser.getId() != user.getId())
                    throw new UserAlreadyExistsException("A user with this email already exists");
            });
            toUpdate.setEmailAddress(user.getEmailAddress());
        }
        if (user.getPassword() != null) toUpdate.setPassword(user.getPassword());
        if (user.getRole() != null) toUpdate.setRole(userStore.findRoleByName(user.getRole().getName().getValue()).orElseThrow(() -> new RoleNotFoundException("name", user.getRole().getName().getValue())));
        toUpdate.isValid();
        return userStore.save(toUpdate);
    }

    public void deleteUser(Long id) {
        if (!hasRole("SYSTEM_ADMIN")) throw new AccessDeniedException();
        userStore.findById(id).orElseThrow(() -> new UserNotFoundException("id", id.toString()));
        userStore.deleteById(id);
    }

    public User requestNewPassword(ResetToken randomKey) {
        User toReset = getAuthenticatedUser();
        if (toReset == null) throw new AccessDeniedException();
        toReset.setResetDate(LocalDateTime.now());
        toReset.setResetToken(randomKey);
        return userStore.save(toReset);
    }

    public User updatePassword(ResetToken key, Password newPassword) {
        if (getAuthenticatedUser() == null) throw new AccessDeniedException();
        User toUpdate = userStore.findById(getAuthenticatedUser().getId()).get();
        if (toUpdate.canChangePassword(key)) {
            toUpdate.setPassword(newPassword);
            toUpdate.setResetToken(new ResetToken(null));
        } else
            throw new IllegalArgumentException("Invalid verifier provided");
        return userStore.save(toUpdate);
    }
}
