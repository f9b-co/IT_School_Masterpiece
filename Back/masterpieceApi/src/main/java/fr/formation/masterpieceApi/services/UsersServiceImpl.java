package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.UsersDto;
import fr.formation.masterpieceApi.dtos.UsersViewDto;
import fr.formation.masterpieceApi.entities.Users;
import fr.formation.masterpieceApi.repositories.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepo;
    
    protected UsersServiceImpl(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }
    
    private void populateAndSave(@NotNull UsersDto dto, @NotNull Users user) {
        // Convert dto to entity:
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDepartment(dto.getDepartment());
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        usersRepo.save(user); // Save to database
    } 
    @Override
    public void create(UsersDto dto) {
        Users user = new Users();
        populateAndSave(dto, user);
    }

    @Override
    public void update(Long id, UsersDto dto) {
        Users user = usersRepo.findById(id).get();
        populateAndSave(dto, user);
    }

    @Override
    public UsersViewDto getOne(Long id) { return usersRepo.getById(id); }

    @Override
    public List<UsersViewDto> getAll(Pageable pageable) {
        return usersRepo.getAllProjectedBy(pageable);
    }

    @Override
    public void delete(Long id) { usersRepo.deleteById(id); }
}
