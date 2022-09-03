package org.example.warehouse.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.DomainModel;
import org.example.warehouse.domain.vo.Description;
import org.example.warehouse.domain.vo.Name;

@Getter
@Setter

public class Role extends DomainModel {
    private Name name;
    private Description description;

    public Role(Long id, Name name, Description description) {
        super(id);

        if (name == null) throw new IllegalArgumentException("Name is required");

        this.name = name;
        this.description = description;
    }
}
