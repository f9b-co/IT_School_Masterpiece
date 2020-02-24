package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.AccountDto;
import fr.formation.masterpieceApi.dtos.AccountViewDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {

    boolean userExists(String username);
    boolean usernameNotUsed(String username);
    void create(AccountDto dto);
    AccountViewDto getOne(String username);
    List<AccountViewDto> getAll(Pageable pageable);
    void delete(String username);
    void changePassword(String username, String password);
}
