package fr.formation.masterpieceApi.dtos;


import fr.formation.masterpieceApi.entities.Activity;
import fr.formation.masterpieceApi.entities.Department;
import fr.formation.masterpieceApi.entities.Team;

import java.util.Set;

public interface EmployeeViewDto {
    String getFirstName();
    String getLastName();
    Department getDepartment();
    Team getTeam();
    String getEmail();
    String getUsername();
    Set<Activity> getActivities();
}
