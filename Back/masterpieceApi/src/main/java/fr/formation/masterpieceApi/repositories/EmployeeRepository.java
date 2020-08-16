package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.EmployeeAuthDto;
import fr.formation.masterpieceApi.dtos.EmployeeInfoDto;
import fr.formation.masterpieceApi.dtos.EmployeeViewDto;
import fr.formation.masterpieceApi.entities.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    EmployeeViewDto readByUsername(String username)  ;
    List<EmployeeViewDto> getAllProjectedBy(Pageable pageable);
    void deleteByUsername(String username);
    Optional<Employee> getByUsername (String username);
    Optional<EmployeeAuthDto> findByUsername (String username);
    Optional<EmployeeInfoDto> getById(Long id);
}
