package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.ActivitiesCreateDto;
import fr.formation.masterpieceApi.dtos.ListedActivitiesViewDto;

import java.util.List;

public interface ActivityService {

    void create(ActivitiesCreateDto dto);

    void delete(Long id);

    List<ListedActivitiesViewDto> getMonthlyListedActivities(Long id, int month);

}
