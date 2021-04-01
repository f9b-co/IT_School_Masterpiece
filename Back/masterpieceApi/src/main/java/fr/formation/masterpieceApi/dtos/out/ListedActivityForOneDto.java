package fr.formation.masterpieceApi.dtos.out;

/*
 * A reduced projection of Activity main fields
 * without id but composed with ActivityViewDto.
 * Aggregated in EmployeeActivitiesDto to project activities list for one employee
 */
public interface ListedActivityForOneDto {

    ActivityViewDto getActivity();
    boolean isValidated();

}
