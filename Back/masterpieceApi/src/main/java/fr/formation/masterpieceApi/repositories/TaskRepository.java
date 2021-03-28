package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.TaskShortDto;
import fr.formation.masterpieceApi.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<TaskShortDto> getAllProjectedBy();

}
