package fr.formation.masterpieceApi.controllers;

import fr.formation.masterpieceApi.dtos.*;
import fr.formation.masterpieceApi.services.ListedActivityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/listed-activities", consumes = "application/json", produces = "application/json")
public class ListedActivityController {

    private final ListedActivityService service;

    protected ListedActivityController(ListedActivityService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('User')")
    @PostMapping
    protected void createAllListed(@Valid @RequestBody List<ListedActivityInputDto> dto) {
        System.out.println(dto.toString());
        service.createAllListed(dto);
    }

    @PreAuthorize("hasAuthority('User')")
    @PatchMapping
    protected void patchAllListed(@Valid @RequestBody List<ListedActivityInputDto> dto) {
        System.out.println(dto.toString());
        service.patchAllListed(dto);
    }

}

