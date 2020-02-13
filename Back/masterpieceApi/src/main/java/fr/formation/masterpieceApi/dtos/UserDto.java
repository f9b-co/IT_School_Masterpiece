package fr.formation.masterpieceApi.dtos;

import fr.formation.masterpieceApi.entities.Role;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UserDto {
    private String firstName;
    private String lastName;
    private String department;
    private String login;
    private  boolean noAccount = true ;
    private String password;
    //private Set<Role> roles;

    public UserDto() {
        //
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public boolean getNoAccount() {return noAccount; }
    public void setNoAccount(boolean noAccount) { this.noAccount = noAccount; }
    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
        if (!(password =="")) { setNoAccount(false); }
    }
    /*public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
        if (isNull) { setIsNull(false); }
    }*/

    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", login='" + login + '\'' +
                ", noAccount=" + noAccount +
                ", password='" + password + '\'' +
                '}';
    }
}
