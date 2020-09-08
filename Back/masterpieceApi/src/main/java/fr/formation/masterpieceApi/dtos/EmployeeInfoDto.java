package fr.formation.masterpieceApi.dtos;

/**
 * A projection of a {@code CustomUser} for user info.
 */
public interface EmployeeInfoDto {

    Long getId();

    String getUsername();

    String getFirstName();

    String getLastName();
}
