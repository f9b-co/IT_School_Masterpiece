package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.dtos.*;
import fr.formation.masterpieceApi.services.ActivityService;
import fr.formation.masterpieceApi.services.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/activities", consumes = "application/json", produces = "application/json")
public class ActivityController {

    private final ActivityService service;

    protected ActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping
    protected void create(@Valid @RequestBody ActivitiesCreateDto dto) {
        System.out.println(dto.toString());
        service.create(dto);
    }

/*    @GetMapping("/listed/filtered")
    protected List<ListedActivitiesViewDto> getAllListed(@RequestParam("mo") int monthOffset, @RequestParam("ui") Long userId) {
        return null; //service.getMonthlyListedActivities(monthOffset, userId);
    }*/

    @GetMapping("/listed")
    protected List<ListedActivitiesViewDto> getAllListed() {
        return service.getAllProjectedBy();
    }

    @DeleteMapping("/{id}")
    protected void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

}

