package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.TeamCreateDto;
import fr.formation.masterpieceApi.dtos.TeamShortInterfaceDto;

import java.util.List;

public interface TeamService {

    void create(TeamCreateDto dto);
    List<TeamShortInterfaceDto> getManagerTeams(Long managerId);
    void delete(String teamName);
}
