package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.AccountViewDto;
import fr.formation.masterpieceApi.entities.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    AccountViewDto getByUsername(String username)  ;
    List<AccountViewDto> getAllProjectedBy(Pageable pageable);
    void deleteByUsername(String username);
    Optional<Account> findByUsername(String username);
}
