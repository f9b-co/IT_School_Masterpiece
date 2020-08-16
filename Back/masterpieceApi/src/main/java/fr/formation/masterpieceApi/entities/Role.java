package fr.formation.masterpieceApi.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role extends AbstractEntity {
    private String code;
    private boolean defaultRole;
    @ManyToMany(mappedBy = "roles")
    public Set<Employee> accounts;

    public Role() {
        //
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public boolean isDefaultRole() { return defaultRole; }
    public void setDefaultRole(boolean defaultRole) { this.defaultRole = defaultRole; }
    public Set<Employee> getAccounts() { return accounts; }
    public void setAccounts(Set<Employee> accounts) { this.accounts = accounts; }
}