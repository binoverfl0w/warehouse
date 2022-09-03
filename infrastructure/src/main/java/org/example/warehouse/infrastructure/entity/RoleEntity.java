package org.example.warehouse.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.warehouse.domain.user.Role;
import org.example.warehouse.domain.vo.Description;
import org.example.warehouse.domain.vo.Name;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "ROLE")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;

    public Role toDomainRole() {
        return new Role(
                id,
                new Name(name),
                new Description(description)
        );
    }

    public static RoleEntity fromDomainRole(Role role) {
        if (role == null) return null;
        RoleEntity mapRole = new RoleEntity();
        mapRole.setId(role.getId());
        mapRole.setName(role.getName().getValue());
        mapRole.setDescription(role.getDescription().getValue());
        return mapRole;
    }
}
