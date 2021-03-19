package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.EmployeeActivitiesDto;
import fr.formation.masterpieceApi.dtos.EmployeeAuthDto;
import fr.formation.masterpieceApi.dtos.EmployeeInfoDto;
import fr.formation.masterpieceApi.dtos.EmployeeViewDto;
import fr.formation.masterpieceApi.entities.Employee;
import fr.formation.masterpieceApi.entities.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> getByUsername (String username);
    Optional<EmployeeAuthDto> findByUsername (String username);
    Optional<EmployeeInfoDto> getById(Long id);
    EmployeeViewDto readByUsername(String username);
    EmployeeActivitiesDto readByUsernameAndListedActivitiesActivityDateStartsWith(String username, String yearMonth);

    List<EmployeeViewDto> getAllByTeam (Team team);
    List<EmployeeInfoDto> getAllProjectedBy(Pageable pageable);

    //void patchByUsername(String username);

    void deleteByUsername(String username);

}
