package fr.formation.masterpieceApi.dtos;

import java.util.Set;

public interface EmployeeActivitiesDto {

    String getFirstName();
    String getLastName();
    String getUsername();
    TeamShortDto getTeam();
    Set<ListedActivitiesForOneDto> getListedActivities();

}
