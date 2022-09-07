package org.example.warehouse.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.DomainModel;
import org.example.warehouse.domain.vo.*;

import java.time.LocalDateTime;

@Getter
@Setter

public class User extends DomainModel {
    private Fullname fullname;
    private Username username;
    private EmailAddress emailAddress;
    private Password password;
    private Role role;
    private LocalDateTime resetDate;
    private ResetToken resetToken;

    public User(Long id, Fullname fullname, Username username, EmailAddress emailAddress, Password password, Role role) {
        super(id);
        this.fullname = fullname;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }

    public User(Long id, Fullname fullname, Username username, EmailAddress emailAddress, Password password, Role role, LocalDateTime resetDate, ResetToken resetToken) {
        super(id);
        this.fullname = fullname;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
        this.resetDate = resetDate;
        this.resetToken = resetToken;
    }

    public void isValid() {
        if (fullname == null) throw new IllegalArgumentException("Fullname is required");
        if (username == null) throw new IllegalArgumentException("Username is required");
        if (emailAddress == null) throw new IllegalArgumentException("Email address is required");
        if (password == null) throw new IllegalArgumentException("Password is required");
        if (role == null) throw new IllegalArgumentException("Role is required");
    }

    public boolean canResetPassword() {
        return resetDate == null || resetDate.plusHours(1).isBefore(LocalDateTime.now());
    }

    public boolean canChangePassword(ResetToken resetToken) {
        return resetDate != null && resetDate.plusHours(1).isAfter(LocalDateTime.now()) && resetToken.getValue().equals(this.resetToken.getValue());
    }
}
