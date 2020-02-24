package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.AccountDto;
import fr.formation.masterpieceApi.dtos.AccountViewDto;
import fr.formation.masterpieceApi.entities.Account;
import fr.formation.masterpieceApi.entities.Role;
import fr.formation.masterpieceApi.repositories.AccountRepository;
import fr.formation.masterpieceApi.repositories.RoleRepository;
import fr.formation.masterpieceApi.repositories.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;

@Service
public class AccountServiceImpl  implements AccountService {

    private final UserRepository usersRepo;
    private final AccountRepository accountsRepo;
    private final RoleRepository rolesRepo;

    protected AccountServiceImpl(UserRepository usersRepo, AccountRepository accountsRepo, RoleRepository rolesRepo) {
        this.usersRepo = usersRepo;
        this.accountsRepo = accountsRepo;
        this.rolesRepo = rolesRepo;
    }

    private void populateAndSave(@NotNull AccountDto dto, @NotNull Account account) {
        account.setEnable(true);
        account.setAccountUsername(dto.getAccountUsername());
        account.setPassword(dto.getPassword());
        Role defaultRole = rolesRepo.findByDefaultRoleTrue().get();
        HashSet<Role> roles = new HashSet<Role>();
        roles.add(defaultRole);
        account.setRoles(roles);
        accountsRepo.save(account);
    }

    @Override
    public boolean userExists(String username) {
        return usersRepo.findByUsername(username).isPresent();
    }

    @Override
    public boolean usernameNotUsed(String accountUsername) {
        return !accountsRepo.findByAccountUsername(accountUsername).isPresent();
    }

    @Override
    public void create(AccountDto dto) {
        if (userExists(dto.getAccountUsername()) && usernameNotUsed(dto.getAccountUsername())) {
            Account account = new Account();
            populateAndSave(dto, account);
        }
    }

    @Override
    public void changePassword(String accountUsername, String password) {
        Account account = accountsRepo.findByAccountUsername(accountUsername).get();
        account.setPassword(password);
        accountsRepo.save(account);
    }

    @Override
    public AccountViewDto getOne(String accountUsername)  { return accountsRepo.getByAccountUsername(accountUsername); }

    @Override
    public List<AccountViewDto> getAll(Pageable pageable)  {
        return accountsRepo.getAllProjectedBy(pageable);
    }

    @Override
    public void delete(String accountUsername) {

    }
}
