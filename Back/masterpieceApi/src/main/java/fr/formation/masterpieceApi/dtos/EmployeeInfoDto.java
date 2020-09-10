package fr.formation.masterpieceApi.dtos;


/**
 * A projection of a {@code Employee} for employee info.
 */
public interface EmployeeInfoDto {

    Long getId();

    String getUsername();

    String getFirstName();

    String getLastName();

    String getDepartment();

    String getEmail();

}
