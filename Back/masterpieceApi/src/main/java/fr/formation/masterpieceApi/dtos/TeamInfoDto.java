package fr.formation.masterpieceApi.dtos;

public interface TeamInfoDto {

    Long getId();
    String getName();
    EmployeeShortDto getManager();

}
