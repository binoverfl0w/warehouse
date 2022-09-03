package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.user.Role;
import org.example.warehouse.domain.vo.Description;
import org.example.warehouse.domain.vo.Name;

import java.io.Serializable;

@Getter
@Setter

public class RoleDTO implements Serializable {
    @JsonProperty(value = "id", index = 0)
    private Long id;

    @JsonProperty(value = "name", index = 1)
    private String name;

    @JsonProperty(value = "description", index = 2)
    private String description;

    public Role toDomainRole() {
        return new Role(
                id,
                name == null ? null : new Name(name),
                description == null ? null : new Description(description)
        );
    }

    public static RoleDTO fromDomainRole(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName().getValue());
        roleDTO.setDescription(role.getDescription().getValue());
        return roleDTO;
    }
}
