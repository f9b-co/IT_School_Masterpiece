package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.UserDto;
import fr.formation.masterpieceApi.dtos.UserViewDto;
import fr.formation.masterpieceApi.entities.User;
import fr.formation.masterpieceApi.repositories.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository usersRepo;
    
    protected UserServiceImpl(UserRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    private void populateAndSave(@NotNull UserDto dto, @NotNull User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDepartment(dto.getDepartment());
        user.setEmail(dto.getEmail());
        user.setLogin(dto.getAccountUsername());
        usersRepo.save(user);
    }

    @Override
    public void create(UserDto dto) {
        User user = new User();
        populateAndSave(dto, user);
        System.out.println(user.toString());
    }
    @Override

    public void update(String accountUsername, UserDto dto) {
        User user = usersRepo.findByAccountUsername(accountUsername).get();//usersRepo.findAll().stream().filter(u -> u.getAccountUsername().equals(accountUsername)).findFirst().get();
        populateAndSave(dto, user);
        System.out.println(user.toString());
    }

    @Override
    public UserViewDto getOne(String login) { return usersRepo.getByLogin(login); }

    @Override
    public List<UserViewDto> getAll(Pageable pageable) {
        return usersRepo.getAllProjectedBy(pageable);
    }

    @Transactional
    @Override
    public void delete(String login) { usersRepo.deleteByLogin(login); }

}
