package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.UserViewDto;
import fr.formation.masterpieceApi.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserViewDto getByLogin(String login) ;
    List<UserViewDto> getAllProjectedBy(Pageable pageable);
    void deleteByLogin(String login);
}
