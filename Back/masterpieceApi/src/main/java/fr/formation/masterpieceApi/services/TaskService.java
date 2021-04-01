package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.in.TaskInputDto;
import fr.formation.masterpieceApi.dtos.out.TaskShortDto;

import java.util.List;

public interface TaskService {

    void create(TaskInputDto dto);

    List<TaskShortDto> getAllTasks();
}
