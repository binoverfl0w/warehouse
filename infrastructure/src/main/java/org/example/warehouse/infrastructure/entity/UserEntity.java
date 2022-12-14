package org.example.warehouse.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.user.Role;
import org.example.warehouse.domain.user.User;
import org.example.warehouse.domain.vo.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FULLNAME", nullable = false)
    private String fullname;
    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private RoleEntity role;
    @Column(name = "RESET_DATE")
    private LocalDateTime resetDate;
    @Column(name = "RESET_TOKEN")
    private String resetToken;

    public User toDomainUser() {
        return new User(
                id,
                new Fullname(fullname),
                new Username(username),
                new EmailAddress(email),
                new Password(password),
                role == null ? null : role.toDomainRole(),
                resetDate,
                new ResetToken(resetToken)
        );
    }

    public static UserEntity fromDomainUser(User user) {
        UserEntity mapUser = new UserEntity();
        mapUser.setId(user.getId());
        mapUser.setFullname(user.getFullname().getValue());
        mapUser.setUsername(user.getUsername().getValue());
        mapUser.setEmail(user.getEmailAddress().getValue());
        mapUser.setPassword(user.getPassword().getValue());
        mapUser.setRole(RoleEntity.fromDomainRole(user.getRole()));
        mapUser.setResetDate(user.getResetDate());
        mapUser.setResetToken(user.getResetToken().getValue());
        return mapUser;
    }
}
