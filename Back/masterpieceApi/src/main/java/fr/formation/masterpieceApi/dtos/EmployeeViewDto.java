package fr.formation.masterpieceApi.dtos;

public interface EmployeeViewDto {

    String getFirstName();
    String getLastName();
    DepartmentShortDto getDepartment();
    TeamShortDto getTeam();
    String getEmail();
    String getUsername();

}
