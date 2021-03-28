package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.*;
import fr.formation.masterpieceApi.entities.Employee;
import fr.formation.masterpieceApi.entities.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> getByUsername (String username);
    Optional<EmployeeViewDto> readByUsername(String username);
    Optional<EmployeeViewDto> findByEmail(String email);
    Optional<EmployeeAuthDto> findByUsername (String username);
    Optional<EmployeeInfoDto> getById(Long id);

/*    @Query("select new fr.formation.masterpieceApi.dtos.EmployeeActivitiesDto" +
            "(e.username, e.firstName, e.lastName, e.teamName, e.listedActivities) " +
            "from Team te join Employee e on te.name = e.teamName " +
            "inner join e.listedActivities la " +
            "inner join la.activity a " +
            "inner join a.task ta " +
            "where e.username = :username " +
            "and a.date like :yearMonth% " )*/
    EmployeeActivitiesDto readByUsernameAndListedActivitiesActivityDateStartsWith(String username, String yearMonth);
    //Optional<EmployeeActivitiesDto> readByUsernameAndListedActivitiesActivityDateStartsWith(String username, String yearMonth);
    //findByUsernameAndActivityDateLike
    List<EmployeeActivitiesDto> readByListedActivitiesActivityDateStartsWith(String yearMonth);

    List<EmployeeShortDto> getAllDistinctByTeam (Team team);
    List<EmployeeInfoDto> getAllProjectedBy(Pageable pageable);

    //void patchByUsername(String username);

    void deleteByUsername(String username);

}
