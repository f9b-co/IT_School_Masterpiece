package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.ListedActivityInputDto;

import java.util.List;

public interface ListedActivityService {

    void createAllListed(List<ListedActivityInputDto> dto);

    void patchAllListed(List<ListedActivityInputDto> dtoList);

    void delete(Long id);

}

