package org.example.warehouse.service;

import org.example.warehouse.domain.DomainService;
import org.example.warehouse.domain.IAuthenticationFacade;
import org.example.warehouse.domain.user.IUserStore;
import org.example.warehouse.domain.user.User;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.exception.AccessDeniedException;

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
}
