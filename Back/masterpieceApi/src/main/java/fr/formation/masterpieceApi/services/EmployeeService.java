package fr.formation.masterpieceApi.services;

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
    List<EmployeeViewDto> getAll(Pageable pageable);
    EmployeeInfoDto getCurrentUserInfo(Long id);
    void delete(String username);

    Department findOne(String DepartmentName);

}
