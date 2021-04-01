package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.in.ListedActivityInputDto;

import java.util.List;

public interface ListedActivityService {

    boolean createAllListed(List<ListedActivityInputDto> dto);

    boolean patchAllListed(List<ListedActivityInputDto> dtoList);

    void delete(Long id);

}

