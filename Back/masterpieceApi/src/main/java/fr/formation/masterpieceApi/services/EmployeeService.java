package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.EmployeeActivitiesDto;
import fr.formation.masterpieceApi.dtos.EmployeeCreateDto;
import fr.formation.masterpieceApi.dtos.EmployeeInfoDto;
import fr.formation.masterpieceApi.dtos.EmployeeViewDto;
import fr.formation.masterpieceApi.entities.Department;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    boolean userExists(String username);
    void create(EmployeeCreateDto dto);
    EmployeeViewDto getOne(String username);
    EmployeeInfoDto getCurrentUserInfo(Long id);
    List<EmployeeInfoDto> getAll(Pageable pageable);
    List<EmployeeViewDto> getUserTeamMembers(String username);
    void delete(String username);

    Department findOne(String DepartmentName);

    EmployeeActivitiesDto getMonthActivities(String username, String yearMonth);
    List<EmployeeActivitiesDto> getAllActivities(String username, String yearMonth);
}
