package fr.formation.masterpieceApi.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="accounts")
public class Account {

    @Column(nullable = false)
    private  boolean enable;
    @Column(length = 7, nullable = false)
    private String username;
    @Column(length = 255)
    private String password;
    @ManyToMany
    private Set<Role> roles;

    public Account() {
        //
    }

    public boolean isEnable() { return enable; }
    public void setEnable(boolean enable) { this.enable = enable; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Role role) { this.roles.add(role); }

    @Override
    public String toString() {
        return "Account{" +
                "enable=" + enable +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
