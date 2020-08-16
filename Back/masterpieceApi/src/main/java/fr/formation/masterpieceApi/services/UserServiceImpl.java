package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.CredentialsDto;
import fr.formation.masterpieceApi.dtos.UserDto;
import fr.formation.masterpieceApi.dtos.UserViewDto;
import fr.formation.masterpieceApi.entities.Role;
import fr.formation.masterpieceApi.entities.User;
import fr.formation.masterpieceApi.repositories.RoleRepository;
import fr.formation.masterpieceApi.repositories.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
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
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDepartment(dto.getDepartment());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getUsername());
        user.setEnable(true);
        Role defaultRole = rolesRepo.findByDefaultRoleTrue().get();
        HashSet<Role> roles = new HashSet<Role>();
        roles.add(defaultRole);
        user.setRoles(roles);
        usersRepo.save(user);
    }

    @Override
    public boolean userExists(String username) {
        return usersRepo.findByUsername(username).isPresent();
    }

    @Override
    public void create(UserDto dto) {
        User user = new User();
        populateAndSave(dto, user);
        System.out.println(user.toString());
    }

    @Override
    public void update(String username, UserDto dto) {
        User user = usersRepo.findByUsername(username).get();//usersRepo.findAll().stream().filter(u -> u.getUsername().equals(username)).findFirst().get();
        populateAndSave(dto, user);
        System.out.println(user.toString());
    }

    @Override
    public void changePassword(CredentialsDto dto) {
        User user = usersRepo.findByUsername(dto.getUsername()).get();
        user.setPassword(dto.getPassword());
        usersRepo.save(user);
    }

    @Override
    public UserViewDto getOne(String username) { return usersRepo.getByUsername(username); }

    @Override
    public List<UserViewDto> getAll(Pageable pageable) {
        return usersRepo.getAllProjectedBy(pageable);
    }

    @Transactional
    @Override
    public void delete(String username) { usersRepo.deleteByUsername(username); }

}
