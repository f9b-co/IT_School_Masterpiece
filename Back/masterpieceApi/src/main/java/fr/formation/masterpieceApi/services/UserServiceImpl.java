package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.UserDto;
import fr.formation.masterpieceApi.dtos.UserViewDto;
import fr.formation.masterpieceApi.entities.Account;
import fr.formation.masterpieceApi.entities.User;
import fr.formation.masterpieceApi.repositories.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository usersRepo;
    
    protected UserServiceImpl(UserRepository usersRepo) {
        this.usersRepo = usersRepo;
    }
    
    private void populateAndSave(@NotNull UserDto dto, @NotNull User user) {
        // Convert dto to entity:
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDepartment(dto.getDepartment());
        user.setLogin(dto.getLogin());
        Account account = new Account();
        account.setPassword(dto.getPassword());
        //account.setRoles(dto.getRoles());

        usersRepo.save(user); // Save to database
    } 
    @Override
    public void create(UserDto dto) {
        User user = new User();
        populateAndSave(dto, user);
    }

    @Override
    public void update(String login, UserDto dto) {
        User user = usersRepo.findAll().stream().filter(u -> u.getLogin().equals(login)).findFirst().get();
        populateAndSave(dto, user);
    }

    @Override
    public UserViewDto getOne(String login) { return usersRepo.getByLogin(login); }

    @Override
    public List<UserViewDto> getAll(Pageable pageable) {
        return usersRepo.getAllProjectedBy(pageable);
    }

    @Override
    public void delete(String login) { usersRepo.deleteByLogin(login); }
}
