package fr.formation.masterpieceApi.dtos.out;

import fr.formation.masterpieceApi.entities.HalfDay;

/*
 * A reduced projection of Activity fields
 * without id and less details on Task
 * Used for composition of ListedActivity Dtos
 */
public interface ActivityViewDto {

    String getDate();
    HalfDay getHalfDay();
    TaskShortDto getTask();

}
