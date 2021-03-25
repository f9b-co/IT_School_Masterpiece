package fr.formation.masterpieceApi.dtos.tmpDto;

public class TeamShortDto {

    private String name;
    private EmployeeShortDto manager;

    public TeamShortDto(String name, EmployeeShortDto manager) {
        this.name = name;
        this.manager = manager;
    }

    public String getName() { return name; }
    public EmployeeShortDto getManager() { return manager; }

}
