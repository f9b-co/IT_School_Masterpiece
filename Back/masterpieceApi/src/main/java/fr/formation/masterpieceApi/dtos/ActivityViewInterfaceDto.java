package fr.formation.masterpieceApi.dtos;

import fr.formation.masterpieceApi.entities.HalfDay;

public interface ActivityViewInterfaceDto {

    String getDate();
    HalfDay getHalfDay();
    TaskShortInterfaceDto getTask();

}
