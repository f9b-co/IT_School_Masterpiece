package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.dtos.ListedActivitiesViewDto;
import fr.formation.masterpieceApi.entities.ListedActivities;
import fr.formation.masterpieceApi.entities.ListedActivitiesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListedActivitiesRepository  extends JpaRepository<ListedActivities, ListedActivitiesId> {

    List<ListedActivitiesViewDto> getAllProjectedBy();
    //List<ListedActivitiesViewDto> getMonthlyListedActivities(int monthOffset, Long userId);

}
