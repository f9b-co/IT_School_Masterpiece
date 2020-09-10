package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.config.SecurityHelper;
import fr.formation.masterpieceApi.dtos.EmployeeChangePasswordDto;
import fr.formation.masterpieceApi.dtos.EmployeeDto;
import fr.formation.masterpieceApi.dtos.EmployeeInfoDto;
import fr.formation.masterpieceApi.dtos.EmployeeViewDto;
import fr.formation.masterpieceApi.services.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin //(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    protected EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    protected void create(@Valid @RequestBody EmployeeDto dto) {
        service.create(dto);
        System.out.println(dto.toString());
    }

    @GetMapping(value = "/{username}", consumes = "application/json", produces = "application/json")
    protected EmployeeViewDto getOne(@PathVariable("username") String username) {
        return service.getOne(username);
    }

    @GetMapping("/userInfo")
    public EmployeeInfoDto currentEmployeeInfo() {
        Long userId = SecurityHelper.getUserId();
        return service.getCurrentUserInfo(userId);
    }

    @GetMapping
    protected List<EmployeeViewDto> getAll(@RequestParam("p") int page, @RequestParam("s") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAll(pageable);
    }

/*//to be activated later
    @PutMapping(value = "/{username}/changeP", consumes = "application/json", produces = "application/json")
    protected void changePassword(@PathVariable("username") String username, @Valid @RequestBody EmployeeChangePasswordDto dto) {
        service.changePassword(dto);
    }*/

    @DeleteMapping("/{username}")
    protected void delete(@PathVariable("username") String username) {
        service.delete(username);
    }

}

