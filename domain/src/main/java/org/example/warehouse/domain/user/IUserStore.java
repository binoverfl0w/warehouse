package org.example.warehouse.domain.user;

import java.util.Optional;

public interface IUserStore {
    Optional<User> findByUsername(String username);
}
