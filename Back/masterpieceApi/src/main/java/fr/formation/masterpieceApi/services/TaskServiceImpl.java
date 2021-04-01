package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.in.TaskInputDto;
import fr.formation.masterpieceApi.dtos.out.TaskShortDto;
import fr.formation.masterpieceApi.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository tasksRepo;

    public TaskServiceImpl(TaskRepository tasksRepo) {
        this.tasksRepo = tasksRepo;
    }

    @Override
    public void create(TaskInputDto dto){
        //
    }

    @Override
    public List<TaskShortDto> getAllTasks(){
        return tasksRepo.getAllProjectedBy();
    }
}
