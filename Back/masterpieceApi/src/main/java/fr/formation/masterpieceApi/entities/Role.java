package fr.formation.masterpieceApi.entities;

import fr.formation.masterpieceApi.utilities.BooleanConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role extends AbstractEntity {

    @Column(length = 64, nullable = false)
    private String name;

    @Convert(converter = BooleanConverter.class)
    @Column(length = 1, nullable = false)
    private boolean defaultRole;

    protected Role() {
        // Empty no-arg constructor (Hibernate)
    }

    public Role(String name, boolean defaultRole) {
        this.name = name;
        this.defaultRole = defaultRole;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isDefaultRole() { return defaultRole; }
    public void setDefaultRole(boolean defaultRole) { this.defaultRole = defaultRole; }
}
