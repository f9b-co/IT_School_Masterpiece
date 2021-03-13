package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.ActivitiesCreateDto;
import fr.formation.masterpieceApi.dtos.ListedActivitiesViewDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Override
    public void create(ActivitiesCreateDto dto) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ListedActivitiesViewDto> getMonthlyListedActivities(Long id, int month) {
        return null;
    }
}
