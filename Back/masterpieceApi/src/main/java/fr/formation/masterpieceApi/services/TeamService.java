package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.in.TeamCreateDto;
import fr.formation.masterpieceApi.dtos.out.TeamShortDto;

import java.util.List;

public interface TeamService {

    void create(TeamCreateDto dto);
    List<TeamShortDto> getManagerTeams(Long managerId);
    void delete(String teamName);
}
