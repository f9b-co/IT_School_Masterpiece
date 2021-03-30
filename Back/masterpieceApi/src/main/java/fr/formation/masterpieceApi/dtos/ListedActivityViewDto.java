package fr.formation.masterpieceApi.dtos;

public interface ListedActivityViewDto {

    EmployeeViewDto getEmployee();
    ActivityViewDto getActivity();
    boolean isValidated();


}
