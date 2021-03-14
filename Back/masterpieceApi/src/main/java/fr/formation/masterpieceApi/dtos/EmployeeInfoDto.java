package fr.formation.masterpieceApi.dtos;


import fr.formation.masterpieceApi.entities.Team;

/**
 * A projection of a {@code Employee} for employee info.
 */
public interface EmployeeInfoDto {

    Long getId();

    boolean isEnabled();

    String getUsername();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getDepartment();

    Team getTeam();

}
