package fr.formation.masterpieceApi.dtos;

import fr.formation.masterpieceApi.entities.Role;

import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserDto {

    @NotBlank
    @Size(max = 64)
    private String firstName;
    @NotBlank
    @Size(max = 64)
    private String lastName;
    @NotBlank
    @Size(max = 64)
    private String department;
    @NotNull
    @Size(max = 64)
    @Email
    private String email;
    @NotBlank
    @Size(min = 7, max = 7)
    private String login;
    @NotNull
    private  boolean noAccount = true ;
    @NotBlank
    @Size(min = 8, max = 20)
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
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
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
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", noAccount=" + noAccount +
                ", password='" + password + '\'' +
                '}';
    }
}
