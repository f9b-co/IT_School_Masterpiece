package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.entities.Department;
import fr.formation.masterpieceApi.services.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/departments", consumes = "application/json", produces = "application/json")
public class DepartmentController {

    private final DepartmentService service;

    protected DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    protected List<Department> getAll() {
        return service.findAll();
    }

}
