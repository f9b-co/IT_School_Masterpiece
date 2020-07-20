package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.dtos.AccountDto;
import fr.formation.masterpieceApi.dtos.UserDto;
import fr.formation.masterpieceApi.dtos.UserViewDto;
import fr.formation.masterpieceApi.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin //(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserAccountController {

    private final UserService service;

    protected UserAccountController(UserService service) {
        this.service = service;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    protected void create(@Valid @RequestBody UserDto dto) {
        service.create(dto);
        System.out.println(dto.toString());
    }

    @GetMapping(value = "/{username}", consumes = "application/json", produces = "application/json")
    protected UserViewDto getOne(@PathVariable("username") String username) {
        return service.getOne(username);
    }

    @GetMapping
    protected List<UserViewDto> getAll(@RequestParam("p") int page, @RequestParam("s") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAll(pageable);
    }

    @PutMapping(value = "/{username}/changeP", consumes = "application/json", produces = "application/json")
    protected void changePassword(@PathVariable("username") String username, @Valid @RequestBody AccountDto dto) {
        service.changePassword(dto);
    }

    @DeleteMapping("/{username}")
    protected void delete(@PathVariable("username") String username) {
        service.delete(username);
    }

}

