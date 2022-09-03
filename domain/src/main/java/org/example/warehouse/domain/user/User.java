package org.example.warehouse.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.DomainModel;
import org.example.warehouse.domain.vo.EmailAddress;
import org.example.warehouse.domain.vo.Fullname;
import org.example.warehouse.domain.vo.Password;
import org.example.warehouse.domain.vo.Username;

@Getter
@Setter

public class User extends DomainModel {
    private Fullname fullname;
    private Username username;
    private EmailAddress emailAddress;
    private Password password;
    private Role role;

    public User(Long id, Fullname fullname, Username username, EmailAddress emailAddress, Password password, Role role) {
        super(id);
        this.fullname = fullname;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }

    public void isValid() {
        if (fullname == null) throw new IllegalArgumentException("Fullname is required");
        if (username == null) throw new IllegalArgumentException("Username is required");
        if (emailAddress == null) throw new IllegalArgumentException("Email address is required");
        if (password == null) throw new IllegalArgumentException("Password is required");
        if (role == null) throw new IllegalArgumentException("Role is required");
    }
}
