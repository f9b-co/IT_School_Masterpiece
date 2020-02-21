package fr.formation.masterpieceApi.entities;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;


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
    private String login;
    @Embedded
    private Account account;

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
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", account=" + account +
                '}';
    }
}
