package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.AccountDto;
import fr.formation.masterpieceApi.dtos.AccountViewDto;
import fr.formation.masterpieceApi.entities.Account;
import fr.formation.masterpieceApi.entities.Role;
import fr.formation.masterpieceApi.repositories.AccountRepository;
import fr.formation.masterpieceApi.repositories.RoleRepository;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AccountServiceImpl  implements AccountService {

    private final AccountRepository accountsRepo;
    private final RoleRepository rolesRepo;

    protected AccountServiceImpl(AccountRepository accountsRepo, RoleRepository rolesRepo) {
        this.accountsRepo = accountsRepo;
        this.rolesRepo = rolesRepo;
    }

    private void populateAndSave(@NotNull AccountDto dto, @NotNull Account account) {
        account.setEnable(true);
        account.setPassword(dto.getPassword());
        Role defaultRole = rolesRepo.findByDefaultRoleTrue().get();
        account.setRoles(defaultRole);

        accountsRepo.save(account); // Save to database
    }

    @Override
    public void create(AccountDto dto) {
        if (!accountsRepo.findByUsername(dto.getUsername()).isPresent()) {
            Account account = new Account();
            populateAndSave(dto, account);
        }
    }

    @Override
    public void changePassword(String username, String password) {
        Account account = accountsRepo.findByUsername(username).get();
        account.setPassword(password);
        accountsRepo.save(account);
    }

    @Override
    public AccountViewDto getOne(String username)  { return accountsRepo.getByUsername(username); }

    @Override
    public List<AccountViewDto> getAll(Pageable pageable)  {
        return accountsRepo.getAllProjectedBy(pageable);
    }

    @Override
    public void delete(String username) {

    }
}
