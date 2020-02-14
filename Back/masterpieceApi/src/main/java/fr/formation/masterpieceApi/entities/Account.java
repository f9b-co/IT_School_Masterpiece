package fr.formation.masterpieceApi.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Account {

    @Column(nullable = false)
    private  boolean noAccount = true ;
    @Column(length = 20)
    private String password;
    /*@ManyToMany
    private Set<Role> roles;*/

    public Account() {
        //
    }

    public boolean getNoAccount() {return noAccount; }
    public void setNoAccount(boolean noAccount) { this.noAccount = noAccount; }
    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
        if (!(password.matches("^R@nd.+$"))) { setNoAccount(false); }
    }
    /*public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
        if (isNull) { setIsNull(false); }
    }*/

    @Override
    public String toString() {
        return "Account{" +
                "noAccount=" + noAccount +
                ", password='" + password + '\'' +
                '}';
    }
}
