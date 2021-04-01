package fr.formation.masterpieceApi.dtos.out;

import java.util.Set;

/*
 *An almost complete projection of Employee useful main fields, without email and department,
 * less details on team but with aggregation of ListedActivityForOneDto interface
 * for employee's set of ListedActivity
 * Used to project activities list for one employee
 */
public interface EmployeeActivitiesDto {
    Long getId();
    String getUsername();
    String getFirstName();
    String getLastName();
    TeamShortDto getTeam();
    Set<ListedActivityForOneDto> getListedActivities();
}
