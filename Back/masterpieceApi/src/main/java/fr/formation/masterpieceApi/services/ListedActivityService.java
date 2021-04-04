package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.in.ListedActivityCreateDto;
import fr.formation.masterpieceApi.dtos.in.ListedActivityUpdateDto;
import fr.formation.masterpieceApi.entities.Employee;
import fr.formation.masterpieceApi.entities.HalfDay;

import java.util.List;

public interface ListedActivityService {

    boolean simultaneousListedActivityForOneEmployeeExists(Employee employee, String date, HalfDay halfDay);

    boolean createAllListed(List<ListedActivityCreateDto> dto);

    boolean patchAllListed(List<ListedActivityUpdateDto> dtoList);

    void delete(Long id);

}

