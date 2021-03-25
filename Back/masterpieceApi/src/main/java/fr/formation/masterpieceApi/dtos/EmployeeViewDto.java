package fr.formation.masterpieceApi.dtos;

public interface EmployeeViewDto {

    String getFirstName();
    String getLastName();
    DepartmentShortDto getDepartment();
    TeamShortInterfaceDto getTeam();
    String getEmail();
    String getUsername();

}
