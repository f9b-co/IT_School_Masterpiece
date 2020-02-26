package fr.formation.masterpieceApi.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {

    @NotNull
    private  boolean enable;
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
    @Size(max = 128)
    @Email
    private String email;
    @NotBlank
    @Size(min = 7, max = 7)
    private String username;

    public UserDto() {
        //
    }

    public boolean isEnable() {return enable;}
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", login='" + username + '\'' +
                '}';
    }
}
