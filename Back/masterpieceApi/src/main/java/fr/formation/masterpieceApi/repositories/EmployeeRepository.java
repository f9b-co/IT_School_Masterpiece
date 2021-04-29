package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.out.*;
import fr.formation.masterpieceApi.entities.Employee;
import fr.formation.masterpieceApi.entities.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<EmployeeShortDto> readByUsername(String username);
    Optional<Employee> findById(Long id);
    Optional<EmployeeViewDto> findByEmail(String email);
    Optional<EmployeeAuthDto> findByUsername (String username);

    EmployeeActivitiesDto readByUsernameAndListedActivitiesActivityDateStartsWith(String username, String yearMonth);

    List<EmployeeShortDto> getAllDistinctByTeam (Team team);
    List<EmployeeInfoDto> getAllProjectedBy(Pageable pageable);

    void deleteByUsername(String username);

}
