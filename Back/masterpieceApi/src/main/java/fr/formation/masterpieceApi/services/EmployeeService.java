package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.in.EmployeeCreateDto;
import fr.formation.masterpieceApi.dtos.out.EmployeeActivitiesDto;
import fr.formation.masterpieceApi.dtos.out.EmployeeInfoDto;
import fr.formation.masterpieceApi.dtos.out.EmployeeShortDto;
import fr.formation.masterpieceApi.entities.Employee;
import fr.formation.masterpieceApi.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    boolean usernameExists(String username);
    boolean emailExists(String username);

    void create(EmployeeCreateDto dto);
    Employee getOneById(Long id) throws ResourceNotFoundException;
    EmployeeShortDto getOneByUsername(String username) throws ResourceNotFoundException;
    List<EmployeeInfoDto> getAll(Pageable pageable);
    void delete(String username);

    List<EmployeeShortDto> getUserTeamMembers(String teamName);
    List<List<EmployeeShortDto>> getManagerTeamsMembers(Long managerId);
    List<EmployeeActivitiesDto> getTeamMonthActivities(String teamName, String yearMonth, String username);
}
