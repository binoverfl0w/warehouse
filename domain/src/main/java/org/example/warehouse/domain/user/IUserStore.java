package org.example.warehouse.domain.user;

import org.example.warehouse.domain.vo.PageVO;

import java.util.Optional;

public interface IUserStore {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    PageVO<User> getUserPage(int page, int size);

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);

    Optional<Role> findRoleByName(String name);

    Optional<User> findByResetToken(String value);
}
