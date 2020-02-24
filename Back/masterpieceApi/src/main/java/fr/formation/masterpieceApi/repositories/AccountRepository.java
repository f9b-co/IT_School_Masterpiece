package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.AccountViewDto;
import fr.formation.masterpieceApi.entities.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    AccountViewDto getByAccountUsername(String accountUsername)  ;
    List<AccountViewDto> getAllProjectedBy(Pageable pageable);
    void deleteByAccountUsername(String username);
    Optional<Account> findByAccountUsername(String accountUsername);
}
