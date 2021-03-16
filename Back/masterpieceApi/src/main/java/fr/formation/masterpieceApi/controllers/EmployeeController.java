package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.dtos.EmployeeActivitiesDto;
import fr.formation.masterpieceApi.dtos.EmployeeCreateDto;
import fr.formation.masterpieceApi.dtos.EmployeeInfoDto;
import fr.formation.masterpieceApi.dtos.EmployeeViewDto;
import fr.formation.masterpieceApi.services.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/employees", consumes = "application/json", produces = "application/json")
public class EmployeeController {

    private final EmployeeService service;

    protected EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    protected void create(@Valid @RequestBody EmployeeCreateDto dto) {
        System.out.println(dto.toString());
        service.create(dto);
    }

    @GetMapping("/{username}/teamMembers/{yearMonth}")
    protected List<EmployeeViewDto> getUserTeamMembersByMonth(@PathVariable("username") String username, @PathVariable("yearMonth") String yearMonth) {
        List<EmployeeViewDto> teamMembers = new ArrayList<>();
        return teamMembers;
    }

    @GetMapping("/{username}/teamsMembers/{yearMonth}")
    protected List<List<EmployeeViewDto>> getManagerTeamsMembersByMonth(@PathVariable("username") String username, @PathVariable("yearMonth") String yearMonth) {
        List<List<EmployeeViewDto>> teamsMembers = new ArrayList<List<EmployeeViewDto>>();
        return teamsMembers;
    }

    @GetMapping("/{username}/activities")
    protected EmployeeActivitiesDto getUserActivities(@PathVariable("username") String username) {
        return service.getOne(username);
    }

    @GetMapping("/{username}/activities/{yearMonth}")
    protected EmployeeActivitiesDto getUserActivities(@PathVariable("username") String username, @PathVariable("yearMonth") String yearMonth) {
        return service.getMonthActivities(username, yearMonth); //.getOne(username);
    }

    @GetMapping("{username}/teamActivities/{yearMonth}") //{username}/teamActivities
    protected List<EmployeeActivitiesDto> getUserTeamActivitiesByMonth(@PathVariable("username") String username, @PathVariable("yearMonth") String yearMonth) {
        return service.getAllActivities(username, yearMonth);
    }

    /*@GetMapping("/userInfo")
    public EmployeeInfoDto currentEmployeeInfo(Long userId) {
        return service.getCurrentUserInfo(userId);
    }
*/
    @GetMapping
    protected List<EmployeeViewDto> getAll(@RequestParam("p") int page, @RequestParam("s") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAll(pageable);
    }

    @DeleteMapping("/{username}")
    protected void delete(@PathVariable("username") String username) {
        service.delete(username);
    }

}

