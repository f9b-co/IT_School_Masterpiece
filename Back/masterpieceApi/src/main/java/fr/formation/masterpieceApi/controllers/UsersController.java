package fr.formation.masterpieceApi.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Users")
public class UsersController {

    private final UsersService service;

    protected UsersController(UsersService service) {
        this.service = service;
    }

    @PostMapping
    protected void create(@Valid @RequestBody UsersDto dto) {
        service.create(dto);
    }

    @GetMapping("/{id}")
    protected UsersViewDto getOne(@PathVariable("id") Long id) {
        return service.getOne(id);
    }

    @GetMapping
    protected List<UsersViewDto> getAll(@RequestParam("p") int page, @RequestParam("s") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAll(pageable);
    }

    @PutMapping("/{id}")
    protected void update(@PathVariable("id") Long id, @Valid @RequestBody UsersDto dto) {
        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    protected void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

}

