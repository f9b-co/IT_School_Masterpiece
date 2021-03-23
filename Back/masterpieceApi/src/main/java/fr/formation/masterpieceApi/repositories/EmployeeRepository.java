package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.*;
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
    Optional<EmployeeViewDto> readByUsername(String username);
    Optional<EmployeeAuthDto> findByUsername (String username);
    Optional<EmployeeInfoDto> getById(Long id);
    EmployeeActivitiesDto readByUsernameAndListedActivitiesActivityDateStartsWith(String username, String yearMonth);
    //Optional<EmployeeActivitiesDto> readByUsernameAndListedActivitiesActivityDateStartsWith(String username, String yearMonth);

    List<EmployeeShortDto> getAllDistinctByTeam (Team team);
    List<EmployeeInfoDto> getAllProjectedBy(Pageable pageable);

    //void patchByUsername(String username);

    void deleteByUsername(String username);

}
