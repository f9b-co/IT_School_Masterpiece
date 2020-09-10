package fr.formation.masterpieceApi.entities;


import fr.formation.masterpieceApi.utilities.BooleanConverter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="employees")
public class Employee extends AbstractEntity {

    @Column(length = 64, nullable = false)
    private String firstName;
    @Column(length = 64, nullable = false)
    private String lastName;
    @Column(length = 64, nullable = false)
    private String department;
    @Column(length = 128, unique = true, nullable = false)
    private String email;
    @Column(length = 7, unique = true, nullable = false)
    private String username;
    @Column(length = 255)
    private String password;
    @Convert(converter = BooleanConverter.class)
    @Column(length = 1, nullable = false)
    private  boolean enabled;
    @Convert(converter = BooleanConverter.class)
    @Column(length = 1, nullable = false)
    private boolean accountNonExpired;
    @Convert(converter = BooleanConverter.class)
    @Column(length = 1, nullable = false)
    private boolean accountNonLocked;
    @Convert(converter = BooleanConverter.class)
    @Column(length = 1, nullable = false)
    private boolean credentialsNonExpired;
    @ManyToMany
    @JoinTable(name = "employees_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    protected Employee() {
        // Empty no-arg constructor (Hibernate)
    }
    /*
     * Creates a new enabled employee using employee's 4 args constructor.
     */
    public Employee(String password, String username, Set<Role> roles) {
        this(password, username, roles, true);
    }
    /*
     * Creates a new employee.     *
     * @param password an encrypted password
     * @param username a unique username
     * @param roles    some roles
     * @param enabled  {@code true} if enabled; {@code false} otherwise
     */
    public Employee(String password, String username, Set<Role> roles, boolean enabled) {
        this.password = password;
        this.username = username;
        this.roles = roles;
        this.enabled = enabled;
    }

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
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public boolean isAccountNonExpired() { return accountNonExpired; }
    public void setAccountNonExpired(boolean accountNonExpired) {  this.accountNonExpired = accountNonExpired; }
    public boolean isAccountNonLocked() { return accountNonLocked;  }
    public void setAccountNonLocked(boolean accountNonLocked) { this.accountNonLocked = accountNonLocked; }
    public boolean isCredentialsNonExpired() { return credentialsNonExpired; }
    public void setCredentialsNonExpired(boolean credentialsNonExpired) { this.credentialsNonExpired = credentialsNonExpired; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enabled +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", roles=" + roles +
                '}';
    }
}

