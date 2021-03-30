package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.entities.Activity;
import fr.formation.masterpieceApi.entities.HalfDay;
import fr.formation.masterpieceApi.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByDateAndHalfDayAndTask(String date, HalfDay halfDay, Task task);

}
