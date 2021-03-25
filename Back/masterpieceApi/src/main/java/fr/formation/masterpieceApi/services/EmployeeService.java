package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.*;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    boolean usernameExists(String username);
    boolean emailExists(String username);
    void create(EmployeeCreateDto dto);
    EmployeeViewDto getOne(String username);
    EmployeeInfoDto getCurrentUserInfo(Long id);
    List<EmployeeInfoDto> getAll(Pageable pageable);
    void delete(String username);

    List<EmployeeShortInterfaceDto> getUserTeamMembers(String teamName);
    List<List<EmployeeShortInterfaceDto>> getManagerTeamsMembers(Long managerId);
    //EmployeeActivitiesInterfaceDto getUserMonthActivities(String username, String yearMonth);
    List<EmployeeActivitiesInterfaceDto> getTeamMonthActivities(String teamName, String yearMonth, String username);
}
