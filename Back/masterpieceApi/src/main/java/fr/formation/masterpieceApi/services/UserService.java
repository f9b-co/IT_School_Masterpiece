package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.UserDto;
import fr.formation.masterpieceApi.dtos.UserViewDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void create(UserDto dto);
    void update(String login, UserDto dto);
    UserViewDto getOne(String login);
    List<UserViewDto> getAll(Pageable pageable);
    void delete(String login);
}
