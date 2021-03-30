package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.TaskInputDto;
import fr.formation.masterpieceApi.dtos.TaskShortDto;

import java.util.List;

public interface TaskService {

    void create(TaskInputDto dto);

    List<TaskShortDto> getAllTasks();
}
