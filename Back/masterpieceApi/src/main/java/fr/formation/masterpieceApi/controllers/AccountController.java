package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.dtos.AccountDto;
import fr.formation.masterpieceApi.dtos.AccountViewDto;
import fr.formation.masterpieceApi.services.AccountService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    protected AccountController(AccountService service) {
        this.service = service;
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    protected void create(@Valid @RequestBody AccountDto dto) {
        service.create(dto);
        System.out.println(dto.toString());
    }

    @GetMapping("/{username}")
    protected AccountViewDto getOne(@PathVariable("username") String username) {
        return service.getOne(username);
    }

    @PatchMapping(value = "/{username}/sp", consumes = "text/html")
    protected void changePassword(@PathVariable("username") String username, @Valid @RequestBody String password) {
        service.changePassword(username, password);
    }
}
