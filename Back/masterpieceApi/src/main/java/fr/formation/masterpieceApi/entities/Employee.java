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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;
    @Column(length = 128, unique = true, nullable = false)
    private String email;
    @Column(length = 7, unique = true, nullable = false)
    private String username;
    @Column(length = 255, nullable = false)
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "assigned_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Set<ListedActivities> listedActivities;

    protected Employee() {
        // Empty no-arg constructor (Hibernate)
    }
    /*
     * Creates a new enabled employee using employee's 4 args constructor.
     */
    public Employee(String username, String password, Set<Role> roles) {
        this(username, password, roles, true);
    }
    /*
     * Creates a new employee:
     * username as a unique username
     * password as an encrypted password
     * roles as some roles
     * enabled = true if enabled, false otherwise
     */
    public Employee(String username, String password, Set<Role> roles, boolean enabled) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
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
    public Set<ListedActivities> getListedActivities() { return listedActivities; }
    public void setListedActivities(Set<ListedActivities> listedActivities) { this.listedActivities = listedActivities; }

    @Override
    public String toString() {
        // password=[PROTECTED], value must not be displayed in logs
        return "Employee{" +
                "firstName= '" + firstName + "\'" +
                ", lastName= '" + lastName + "\'" +
                ", department= '" + department.getName() + "\'" +
                ", team= '" + team.getName() + "\'" +
                ", email= '" + email + "\'" +
                ", username= '" + username + "\'" +
                ", password= '[PROTECTED]\'" +
                ", enable= '" + enabled + "\'" +
                ", accountNonExpired= '" + accountNonExpired + "\'" +
                ", accountNonLocked= '" + accountNonLocked + "\'" +
                ", credentialsNonExpired= '" + credentialsNonExpired + "\'" +
                ", roles= '" + roles.toString() + "\'" +
                ", activities= '" + listedActivities.size() + "\'" +
                '}';
    }
}

