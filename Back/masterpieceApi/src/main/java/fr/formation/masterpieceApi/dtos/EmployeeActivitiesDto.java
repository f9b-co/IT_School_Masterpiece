package fr.formation.masterpieceApi.dtos;

import java.util.Set;

public interface EmployeeActivitiesDto {
    Long getId();
    String getUsername();
    String getFirstName();
    String getLastName();
    TeamShortDto getTeam();
    Set<ListedActivityForOneDto> getListedActivities();
}
