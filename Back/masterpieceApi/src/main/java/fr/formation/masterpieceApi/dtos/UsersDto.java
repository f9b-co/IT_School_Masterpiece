package fr.formation.masterpieceApi.dtos;

import javax.validation.constraints.NotBlank;

public class UsersDto {
    private String firstName;
    private String lastName;
    private String department;
    @NotBlank
    private String login;
    @NotBlank
    private String password;

    public UsersDto() {
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
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
