package fr.formation.masterpieceApi.entities;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User extends AbstractEntity {

    @Column(length = 64, nullable = false)
    private String firstName;
    @Column(length = 64, nullable = false)
    private String lastName;
    @Column(length = 64, nullable = false)
    private String department;
    @Column(length = 128, nullable = false)
    private String email;
    @Column(length = 7, nullable = false)
    private String accountUsername;

    public User() {
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
    public String getLogin() { return accountUsername; }
    public void setLogin(String login) { this.accountUsername = login; }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", accountUsername='" + accountUsername + '\'' +
                '}';
    }
}
