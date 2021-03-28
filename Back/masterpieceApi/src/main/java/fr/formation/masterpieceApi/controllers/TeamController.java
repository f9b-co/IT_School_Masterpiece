package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.dtos.TeamCreateDto;
import fr.formation.masterpieceApi.dtos.TeamShortDto;
import fr.formation.masterpieceApi.services.TeamService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/teams", consumes = "application/json", produces = "application/json")
public class TeamController {

    private final TeamService service;

    protected TeamController(TeamService service) {
        this.service = service;
    }

    @PostMapping
    protected void create(@Valid @RequestBody TeamCreateDto dto) {
        System.out.println(dto.toString());
        service.create(dto);
    }

    @GetMapping("/{managerId}")
    protected List<TeamShortDto> getManagerTeams(@PathVariable("managerId") Long managerId) {
        return service.getManagerTeams(managerId);
    }

}
