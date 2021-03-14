package fr.formation.masterpieceApi.repositories;

import fr.formation.masterpieceApi.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {



}
