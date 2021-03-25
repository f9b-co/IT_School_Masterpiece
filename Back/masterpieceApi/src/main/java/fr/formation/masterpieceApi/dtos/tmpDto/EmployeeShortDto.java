package fr.formation.masterpieceApi.dtos.tmpDto;

public class EmployeeShortDto {

    private String username;
    private String firstName;
    private String lastName;
    private TeamShortDto team;

    public EmployeeShortDto(String username, String firstName, String lastName, TeamShortDto team) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
    }

    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public TeamShortDto getTeam() { return team; }

}
