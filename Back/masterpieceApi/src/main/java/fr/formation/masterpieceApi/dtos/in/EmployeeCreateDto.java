package fr.formation.masterpieceApi.dtos.in;

import validations.UniqueEmail;
import validations.UniqueUsername;
import javax.validation.constraints.*;

public class EmployeeCreateDto {

    private final String noSpecialsPattern = "^[^<>=+*~&|\\\\()\\[\\]\"'`,.;:!?@#$%]*$";
    private final String passPattern = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9]).*$|" +
            "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\W).*$|" +
            "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?\\W).*$|" +
            "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?\\W).*$";
    private final String usernamePattern = "^[axAX][0-9]{6}$";

    @NotBlank
    @Size(max = 64)
    @Pattern(regexp = noSpecialsPattern)
    private String firstName;
    @NotBlank
    @Size(max = 64)
    @Pattern(regexp = noSpecialsPattern)
    private String lastName;
    @NotBlank
    @Size(max = 64)
    private String department;
    @UniqueEmail
    @NotNull
    @Size(max = 128)
    @Email
    private String email;
    @UniqueUsername
    @NotBlank
    @Size(min = 7, max = 7)
    @Pattern(regexp = usernamePattern)
    private String username;
    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = passPattern)
    private String password;

    protected EmployeeCreateDto() {
        // Empty no-arg constructor (Hibernate)
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() {return password;}
}
