package fr.formation.masterpieceApi.dtos;

import fr.formation.masterpieceApi.entities.Role;

import java.util.Set;

/*
 * A projection of an Employee for authentication.
 */
public interface EmployeeAuthDto {

    Long getId();

    String getUsername();

    String getPassword();

    Set<Role> getRoles();

    boolean isEnabled();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();
}
