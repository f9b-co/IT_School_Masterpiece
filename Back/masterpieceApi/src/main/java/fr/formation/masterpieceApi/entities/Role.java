package fr.formation.masterpieceApi.entities;

import fr.formation.masterpieceApi.utilities.BooleanConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role extends AbstractEntity {

    private String name;

    @Convert(converter = BooleanConverter.class)
    @Column(length = 1, nullable = false)
    private boolean defaultRole;

    public Role() {
        //
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isDefaultRole() { return defaultRole; }
    public void setDefaultRole(boolean defaultRole) { this.defaultRole = defaultRole; }
}
