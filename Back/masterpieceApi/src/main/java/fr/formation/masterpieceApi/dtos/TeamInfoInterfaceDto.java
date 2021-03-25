package fr.formation.masterpieceApi.dtos;

public interface TeamInfoInterfaceDto {

    Long getId();
    String getName();
    EmployeeShortInterfaceDto getManager();

}
