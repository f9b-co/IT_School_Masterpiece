package fr.formation.masterpieceApi.dtos.out;

/*
 * A very shortened projection of Employee main fields
 * without id, email, department and less details on Department and team
 * Used in Dto composition where no more is needed
 */
public interface EmployeeShortDto {

    String getUsername();
    String getFirstName();
    String getLastName();
    TeamShortDto getTeam();

}
