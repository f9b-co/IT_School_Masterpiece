package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.dtos.UserDto;
import fr.formation.masterpieceApi.dtos.UserViewDto;
import fr.formation.masterpieceApi.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Users")
public class UserController {

    private final UserService service;

    protected UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    protected void create(@Valid @RequestBody UserDto dto) {
        service.create(dto);
    }

    @GetMapping("/{login}")
    protected UserViewDto getOne(@PathVariable("login") String login) {
        return service.getOne(login);
    }

    @GetMapping
    protected List<UserViewDto> getAll(@RequestParam("p") int page, @RequestParam("s") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAll(pageable);
    }

    @PutMapping("/{login}")
    protected void update(@PathVariable("login") String login, @Valid @RequestBody UserDto dto) {
        service.update(login, dto);
    }

    @DeleteMapping("/{login}")
    protected void delete(@PathVariable("login") String login) {
        service.delete(login);
    }

}

