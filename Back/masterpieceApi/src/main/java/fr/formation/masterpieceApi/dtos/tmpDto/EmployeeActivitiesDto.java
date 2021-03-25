package fr.formation.masterpieceApi.dtos.tmpDto;


import fr.formation.masterpieceApi.entities.ListedActivities;

import java.util.Set;

public class EmployeeActivitiesDto {

    private String username;
    private String firstName;
    private String lastName;
    private String teamName;
    private Set<ListedActivities> listedActivities;

    public EmployeeActivitiesDto(String username, String firstName, String lastName, String teamName,
                                 Set<ListedActivities> listedActivities) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamName = teamName;
        this.listedActivities = listedActivities;
    }

    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public String getTeamName() {
        return teamName;
    }

    public Set<ListedActivities> getListedActivities() {
        return listedActivities;
    }
}
