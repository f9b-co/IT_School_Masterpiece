package fr.formation.masterpieceApi.dtos.out;

import fr.formation.masterpieceApi.entities.Department;
import fr.formation.masterpieceApi.entities.Team;

/*
 * An almost complete projection of Employee useful main fields
 * (miss only some not yet used boolean dedicated to advanced auth management)
 * for admin purpose.
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
