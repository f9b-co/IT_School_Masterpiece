package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.dtos.TaskCreateDto;
import fr.formation.masterpieceApi.dtos.TaskShortInterfaceDto;
import fr.formation.masterpieceApi.services.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/tasks", consumes = "application/json", produces = "application/json")
public class TaskController {

    private final TaskService service;

    protected TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    protected void create(@Valid @RequestBody TaskCreateDto dto) {
        System.out.println(dto.toString());
        service.create(dto);
    }

    @GetMapping
    protected List<TaskShortInterfaceDto> getAllTasks() {
        return service.getAllTasks();
    }

}
