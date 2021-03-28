package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.TaskCreateDto;
import fr.formation.masterpieceApi.dtos.TaskShortDto;

import java.util.List;

public interface TaskService {

    void create(TaskCreateDto dto);

    List<TaskShortDto> getAllTasks();
}
