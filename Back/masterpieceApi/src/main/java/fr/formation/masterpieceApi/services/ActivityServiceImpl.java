package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.ActivitiesCreateDto;
import fr.formation.masterpieceApi.dtos.ListedActivitiesViewDto;
import fr.formation.masterpieceApi.repositories.ActivityRepository;
import fr.formation.masterpieceApi.repositories.ListedActivitiesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl extends AbstractService implements ActivityService {

    private final ActivityRepository activityRepo;
    private final ListedActivitiesRepository listedActivitiesRepo;

    public ActivityServiceImpl(ActivityRepository activityRepo, ListedActivitiesRepository listedActivitiesRepo) {
        this.activityRepo = activityRepo;
        this.listedActivitiesRepo = listedActivitiesRepo;
    }

    @Override
    public void create(ActivitiesCreateDto dto) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ListedActivitiesViewDto> getAllProjectedBy() {
        return listedActivitiesRepo.getAllProjectedBy();
    }

/*    @Override
    public List<ListedActivitiesViewDto> getMonthlyListedActivities(int monthOffset, Long userId) {
        return null;
    }*/
}
