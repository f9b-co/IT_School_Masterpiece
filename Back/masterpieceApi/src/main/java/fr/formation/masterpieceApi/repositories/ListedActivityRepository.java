package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.entities.Activity;
import fr.formation.masterpieceApi.entities.Employee;
import fr.formation.masterpieceApi.entities.HalfDay;
import fr.formation.masterpieceApi.entities.ListedActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ListedActivityRepository extends JpaRepository<ListedActivity, Long> {

    Optional<ListedActivity> findById(Long id);
    Optional<ListedActivity> findByEmployeeAndActivity(Employee employee, Activity activity);

    Optional<ListedActivity> findByEmployeeAndActivityDateAndActivityHalfDay(Employee employee, String date, HalfDay halfDay);
}
