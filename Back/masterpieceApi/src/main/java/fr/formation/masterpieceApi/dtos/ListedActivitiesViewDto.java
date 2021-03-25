package fr.formation.masterpieceApi.dtos;

import fr.formation.masterpieceApi.entities.Activity;
import fr.formation.masterpieceApi.entities.Employee;

public interface ListedActivitiesViewDto {

    EmployeeViewDto getEmployee();
    ActivityViewInterfaceDto getActivity();
    boolean isValidated();


}
