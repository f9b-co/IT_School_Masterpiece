package fr.formation.masterpieceApi.dtos;

import java.util.Set;

public interface EmployeeActivitiesInterfaceDto {
    String getUsername();
    String getFirstName();
    String getLastName();
    TeamShortInterfaceDto getTeam();
    Set<ListedActivitiesForOneInterfaceDto> getListedActivities();
}
