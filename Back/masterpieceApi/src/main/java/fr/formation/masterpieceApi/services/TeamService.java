package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.TeamCreateDto;
import fr.formation.masterpieceApi.dtos.TeamShortDto;

import java.util.List;

public interface TeamService {

    void create(TeamCreateDto dto);
    List<TeamShortDto> getManagerTeams(Long managerId);
    void delete(String teamName);
}
