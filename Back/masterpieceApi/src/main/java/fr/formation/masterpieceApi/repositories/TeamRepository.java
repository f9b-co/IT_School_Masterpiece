package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.TeamShortDto;
import fr.formation.masterpieceApi.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName (String teamName);
    Optional<Team> findById (Long id);

    List<TeamShortDto> findAllByManagerId(Long id);


}
