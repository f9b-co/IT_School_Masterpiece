package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.TaskShortDto;
import fr.formation.masterpieceApi.entities.Activity;
import fr.formation.masterpieceApi.entities.HalfDay;
import fr.formation.masterpieceApi.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByColor(String color);
    List<TaskShortDto> getAllProjectedBy();

}
