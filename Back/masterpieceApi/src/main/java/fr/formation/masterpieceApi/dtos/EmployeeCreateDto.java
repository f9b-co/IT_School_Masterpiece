package fr.formation.masterpieceApi.dtos;

import javax.validation.constraints.*;

public class EmployeeCreateDto {

    private final String passPattern = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9]).*$|" +
            "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\W).*$|" +
            "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?\\W).*$|" +
            "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?\\W).*$";

    @NotNull
    private  boolean enabled;
    @NotBlank
    @Size(max = 64)
    private String firstName;
    @NotBlank
    @Size(max = 64)
    private String lastName;
    @NotBlank
    @Size(max = 64)
    private String departmentName;
    @NotNull
    @Size(max = 128)
    @Email
    private String email;
    @NotBlank
    @Size(min = 7, max = 7)
    private String username;
    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = passPattern)
    private String password;

    public EmployeeCreateDto() {
        //
    }

    public void setEnabled(boolean enabled) {this.enabled = enabled;}
    public boolean isEnabled() {return enabled;}
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
