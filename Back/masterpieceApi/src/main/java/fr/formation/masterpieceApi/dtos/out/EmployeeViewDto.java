package fr.formation.masterpieceApi.dtos.out;

/*
 * A reduced projection of Employee main fields
 * without id and less details on Department and team
 * for standard views
 */
public interface EmployeeViewDto {

    String getFirstName();
    String getLastName();
    DepartmentShortDto getDepartment();
    TeamShortDto getTeam();
    String getEmail();
    String getUsername();

}
