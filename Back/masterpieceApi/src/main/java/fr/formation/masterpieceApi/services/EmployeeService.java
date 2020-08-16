package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.CredentialsDto;
import fr.formation.masterpieceApi.dtos.EmployeeDto;
import fr.formation.masterpieceApi.dtos.EmployeeInfoDto;
import fr.formation.masterpieceApi.dtos.EmployeeViewDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface EmployeeService extends UserDetailsService {

    boolean userExists(String username);
    void create(EmployeeDto dto);
    void changePassword(CredentialsDto dto);
    EmployeeViewDto getOne(String username);
    List<EmployeeViewDto> getAll(Pageable pageable);
    EmployeeInfoDto getCurrentUserInfo(Long id);
    void delete(String username);

}
