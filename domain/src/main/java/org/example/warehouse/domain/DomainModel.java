package org.example.warehouse.domain;

import java.io.Serializable;
import java.util.Objects;

public class DomainModel {
    private final Long id;

    public DomainModel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.id == null || o == null || !(this.getClass().equals(o.getClass()))) return false;
        DomainModel dm = (DomainModel) o;
        return dm.id.equals(dm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
