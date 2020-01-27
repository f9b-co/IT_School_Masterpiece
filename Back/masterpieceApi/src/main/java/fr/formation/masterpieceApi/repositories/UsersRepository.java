package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.UsersViewDto;
import fr.formation.masterpieceApi.entities.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    UsersViewDto getById(Long id);
    List<UsersViewDto> getAllProjectedBy(Pageable pageable);
}
