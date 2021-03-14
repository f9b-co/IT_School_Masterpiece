package fr.formation.masterpieceApi.dtos;

import fr.formation.masterpieceApi.entities.HalfDay;
import fr.formation.masterpieceApi.entities.Task;

import java.time.LocalDate;

public interface ActivityViewDto {

    LocalDate getActivityDate();
    HalfDay getHalfDay();
    TaskShortDto getTask();

}
