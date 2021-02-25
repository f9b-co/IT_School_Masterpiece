package fr.formation.masterpieceApi.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role extends AbstractEntity {
    private String name;
    private boolean defaultRole;
    @ManyToMany(mappedBy = "roles")
    public Set<Employee> employees;

    public Role() {
        //
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isDefaultRole() { return defaultRole; }
    public void setDefaultRole(boolean defaultRole) { this.defaultRole = defaultRole; }
    public Set<Employee> getEmployees() { return employees; }
    public void setEmployees(Set<Employee> employees) { this.employees = employees; }
}
