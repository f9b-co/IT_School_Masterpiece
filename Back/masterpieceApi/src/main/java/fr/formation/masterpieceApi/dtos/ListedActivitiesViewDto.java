package fr.formation.masterpieceApi.dtos;

public interface ListedActivitiesViewDto {

    EmployeeViewDto getEmployee();
    ActivityViewDto getActivity();
    boolean isValidated();


}
