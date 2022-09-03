package org.example.warehouse.domain.user;

import org.example.warehouse.domain.vo.PageVO;

import java.util.Optional;

public interface IUserStore {
    Optional<User> findByUsername(String username);

    PageVO<User> getUserPage(int page, int size);

    Optional<User> findById(Long id);
}
