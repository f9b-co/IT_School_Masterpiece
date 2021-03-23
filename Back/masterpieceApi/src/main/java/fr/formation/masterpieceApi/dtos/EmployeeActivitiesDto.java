package fr.formation.masterpieceApi.dtos;

import fr.formation.masterpieceApi.entities.Team;

import java.util.Set;

public interface EmployeeActivitiesDto {

    String getFirstName();
    String getLastName();
    String getUsername();
    Team getTeam();
    Set<ListedActivitiesForOneDto> getListedActivities();

}
