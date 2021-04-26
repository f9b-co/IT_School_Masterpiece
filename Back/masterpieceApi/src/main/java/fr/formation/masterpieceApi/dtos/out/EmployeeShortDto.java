package fr.formation.masterpieceApi.dtos.out;

/*
 * A very shortened projection of Employee main fields
 * without email, department and less details on team
 * Used in Dto composition where no more is needed
 */
public interface EmployeeShortDto {

    Long getId();
    String getUsername();
    String getFirstName();
    String getLastName();
    TeamShortDto getTeam();

}
