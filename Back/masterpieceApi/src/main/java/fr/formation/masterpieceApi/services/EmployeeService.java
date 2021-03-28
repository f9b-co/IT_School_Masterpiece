package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.*;
import fr.formation.masterpieceApi.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    boolean usernameExists(String username);
    boolean emailExists(String username);
    void create(EmployeeCreateDto dto);
    EmployeeViewDto getOne(String username) throws ResourceNotFoundException;
    EmployeeInfoDto getCurrentUserInfo(Long id) throws ResourceNotFoundException;
    List<EmployeeInfoDto> getAll(Pageable pageable);
    void delete(String username);

    List<EmployeeShortDto> getUserTeamMembers(String teamName);
    List<List<EmployeeShortDto>> getManagerTeamsMembers(Long managerId);
    //EmployeeActivitiesDto getUserMonthActivities(String username, String yearMonth);
    List<EmployeeActivitiesDto> getTeamMonthActivities(String teamName, String yearMonth, String username);
}
