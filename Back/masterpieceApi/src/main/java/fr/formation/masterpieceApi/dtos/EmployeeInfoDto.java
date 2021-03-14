package fr.formation.masterpieceApi.dtos;

import fr.formation.masterpieceApi.entities.Department;
import fr.formation.masterpieceApi.entities.Team;

/*
 * A projection of Employee main info-.
 */
public interface EmployeeInfoDto {

    Long getId();
    boolean isEnabled();
    String getUsername();
    String getFirstName();
    String getLastName();
    String getEmail();
    Department getDepartment();
    Team getTeam();

}
