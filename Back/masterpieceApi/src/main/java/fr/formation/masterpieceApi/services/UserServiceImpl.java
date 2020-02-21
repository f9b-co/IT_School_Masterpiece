package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.UserDto;
import fr.formation.masterpieceApi.dtos.UserViewDto;
import fr.formation.masterpieceApi.entities.Account;
import fr.formation.masterpieceApi.entities.Role;
import fr.formation.masterpieceApi.entities.User;
import fr.formation.masterpieceApi.repositories.RoleRepository;
import fr.formation.masterpieceApi.repositories.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository usersRepo;
    private final RoleRepository rolesRepo;
    
    protected UserServiceImpl(UserRepository usersRepo, RoleRepository rolesRepo) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
    }


    private void populateAndSave(@NotNull UserDto dto, @NotNull User user) {
        // Convert dto to entity:
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDepartment(dto.getDepartment());
        user.setEmail(dto.getEmail());
        user.setLogin(dto.getAccountUsername());
        Account account = new Account();
        account.setNoInit(true);
        account.setEnable(true);
        account.setPassword(dto.getPassword());
        Role defaultRole = rolesRepo.findByDefaultRoleTrue().get();
        account.setRoles(defaultRole);
        user.setAccount(account);

        usersRepo.save(user); // Save to database
    }

    @Override
    public void create(UserDto dto) {
        User user = new User();
        populateAndSave(dto, user);
        System.out.println(user.toString());
    }

    @Override
    public void update(String login, UserDto dto) {
        User user = usersRepo.findByLogin(login).get();//usersRepo.findAll().stream().filter(u -> u.getLogin().equals(login)).findFirst().get();
        populateAndSave(dto, user);
        System.out.println(user.toString());
    }

    @Override
    public void changePassword(String login, String password) {
        User user = usersRepo.findByLogin(login).get();//User user = usersRepo.findAll().stream().filter(u -> u.getLogin().equals(login)).findFirst().get();
        Account account = user.getAccount();
        account.setNoInit(false);
        account.setPassword(password);
        usersRepo.save(user);
    }

    @Override
    public UserViewDto getOne(String login) {
        return usersRepo.getByLogin(login); }

    @Override
    public List<UserViewDto> getAll(Pageable pageable) {
        return usersRepo.getAllProjectedBy(pageable);
    }

    @Transactional
    @Override
    public void delete(String login) { usersRepo.deleteByLogin(login); }

}
