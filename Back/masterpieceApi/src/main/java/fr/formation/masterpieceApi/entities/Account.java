package fr.formation.masterpieceApi.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="accounts")
public class Account extends AbstractEntity {

    @OneToOne(mappedBy="account")
    private User user;
    @Column(length = 7, unique = true, nullable = false)
    private String accountUsername;
    @Column(length = 255)
    private String password;
    @Column(nullable = false)
    private  boolean enable;
    @ManyToMany
    private Set<Role> roles;

    public Account() {
        //
    }

    public String getAccountUsername() { return accountUsername; }
    public void setAccountUsername(String accountUsername) { this.accountUsername = accountUsername; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isEnable() { return enable; }
    public void setEnable(boolean enable) { this.enable = enable; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    //public void setRoles(Role role) { this.roles.add(role); }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + accountUsername + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable +
                ", roles=" + roles +
                '}';
    }
}
