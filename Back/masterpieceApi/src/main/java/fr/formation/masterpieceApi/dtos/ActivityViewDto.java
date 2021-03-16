package fr.formation.masterpieceApi.dtos;

import fr.formation.masterpieceApi.entities.HalfDay;

public interface ActivityViewDto {

    String getDate();
    HalfDay getHalfDay();
    TaskShortDto getTask();

}
