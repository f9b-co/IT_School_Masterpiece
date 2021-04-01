package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.dtos.in.TaskInputDto;
import fr.formation.masterpieceApi.dtos.out.TaskShortDto;
import fr.formation.masterpieceApi.services.TaskService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    protected void create(@Valid @RequestBody TaskInputDto dto) {
        System.out.println(dto.toString());
        service.create(dto);
    }

    @GetMapping
    protected List<TaskShortDto> getAllTasks() {
        return service.getAllTasks();
    }

}
