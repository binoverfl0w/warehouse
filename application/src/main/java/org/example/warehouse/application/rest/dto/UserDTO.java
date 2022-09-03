package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.user.User;
import org.example.warehouse.domain.vo.EmailAddress;
import org.example.warehouse.domain.vo.Fullname;
import org.example.warehouse.domain.vo.Password;
import org.example.warehouse.domain.vo.Username;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter

public class UserDTO implements UserDetails, Serializable {
    @JsonProperty(value = "id", index = 0)
    private Long id;

    @JsonProperty(value = "fullname", index = 1)
    private String fullname;

    @JsonProperty(value = "username", index = 2)
    private String username;

    @JsonProperty(value = "email", index = 3)
    private String email;

    @JsonProperty(value = "password", index = 4, access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(value = "authority", index = 5)
    private RoleDTO role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) return null;
        else return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User toDomainUser() {
        return new User(
                id,
                new Fullname(fullname),
                new Username(username),
                new EmailAddress(email),
                new Password(password),
                role == null ? null : role.toDomainRole()
        );
    }

    public static UserDTO fromDomainUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullname(user.getFullname().getValue());
        userDTO.setUsername(user.getUsername().getValue());
        userDTO.setEmail(user.getEmailAddress().getValue());
        userDTO.setPassword(user.getPassword().getValue());
        userDTO.setRole(RoleDTO.fromDomainRole(user.getRole()));
        return userDTO;
    }
}
