package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.user.User;
import org.example.warehouse.domain.vo.*;
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

    @JsonIgnore
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

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User toDomainUser() {
        return new User(
                id,
                fullname == null ? null : new Fullname(fullname),
                username == null ? null : new Username(username),
                email == null ? null : new EmailAddress(email),
                password == null ? null : new Password(password),
                role == null ? null : role.toDomainRole(),
                null,
                new ResetToken(null)
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
