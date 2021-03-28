package fr.formation.masterpieceApi.dtos;

import java.util.Set;

public interface EmployeeActivitiesDto {
    String getUsername();
    String getFirstName();
    String getLastName();
    TeamShortDto getTeam();
    Set<ListedActivitiesForOneDto> getListedActivities();
}
