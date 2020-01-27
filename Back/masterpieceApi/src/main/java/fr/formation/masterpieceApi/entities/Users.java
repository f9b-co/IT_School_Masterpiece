package fr.formation.masterpieceApi.entities;

import javax.persistence.Column;

public class Users extends AbstractEntity {
    @Column(length = 64, nullable = true)
    private String firstName;
    @Column(length = 64, nullable = true)
    private String lastName;
    @Column(length = 64, nullable = true)
    private String department;
    @Column(length = 7, nullable = false)
    private String login;
    @Column(length = 20, nullable = false)
    private String password;

    public Users() {
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
