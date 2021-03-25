package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.TeamCreateDto;
import fr.formation.masterpieceApi.dtos.TeamShortInterfaceDto;
import fr.formation.masterpieceApi.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl extends AbstractService implements TeamService {

    private final TeamRepository teamsRepo;

    public TeamServiceImpl(TeamRepository teamsRepo) {
        this.teamsRepo = teamsRepo;
    }

    @Override
    public void create(TeamCreateDto dto) {

    }

    @Override
    public List<TeamShortInterfaceDto> getManagerTeams(Long managerId) {
        return teamsRepo.findAllByManagerId(managerId);
    }

    @Override
    public void delete(String teamName) {

    }
}
