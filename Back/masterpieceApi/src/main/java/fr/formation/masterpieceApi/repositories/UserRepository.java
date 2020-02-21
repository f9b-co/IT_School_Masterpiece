package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.UserViewDto;
import fr.formation.masterpieceApi.entities.Role;
import fr.formation.masterpieceApi.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserViewDto getByLogin(String login)  ;
    List<UserViewDto> getAllProjectedBy(Pageable pageable);
    void deleteByLogin(String login);
    Optional<User> findByLogin (String login);

}
