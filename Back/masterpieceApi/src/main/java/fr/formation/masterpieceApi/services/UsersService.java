package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.UsersDto;
import fr.formation.masterpieceApi.dtos.UsersViewDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsersService {
    void create(UsersDto dto);
    void update(Long id, UsersDto dto);
    UsersViewDto getOne(Long id);
    List<UsersViewDto> getAll(Pageable pageable);
    void delete(Long id);
}
