package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.dtos.*;
import fr.formation.masterpieceApi.services.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


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

    @PreAuthorize("hasAuthority('User') or hasAuthority('Manager')")
    @GetMapping("/{username}")
    protected EmployeeViewDto getOne(@PathVariable("username") String username) {
        return service.getOne(username);
    }

    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/{username}/team-members")
    protected List<EmployeeShortDto> getUserTeamMembers(@PathVariable("username") String username) {
        return service.getUserTeamMembers(username);
    }

    @PreAuthorize("hasAuthority('Manager')")
    @GetMapping("/{id}/teams-members")
    protected List<List<EmployeeShortDto>> getManagerTeamsMembers(@PathVariable("id") Long id) {
        return service.getManagerTeamsMembers(id);
    }

    @PreAuthorize("hasAuthority('User') or hasAuthority('Manager')")
    @GetMapping("/listed-activities")
    protected List<EmployeeActivitiesDto> getTeamActivitiesByMonth(@RequestParam("team") String teamName, @RequestParam("period") String yearMonth, @RequestParam("username") String username) {
        return service.getTeamMonthActivities(teamName, yearMonth, username);
    }

    /*@GetMapping("/userInfo")
    public EmployeeInfoDto currentEmployeeInfo(Long userId) {
        return service.getCurrentUserInfo(userId);
    }
*/

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping
    protected List<EmployeeInfoDto> getAll(@RequestParam("p") int page, @RequestParam("s") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAll(pageable);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/{username}")
    protected void delete(@PathVariable("username") String username) {
        service.delete(username);
    }

}

