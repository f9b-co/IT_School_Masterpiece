package fr.formation.masterpieceApi.dtos.tmpDto;

public class TeamInfoDto {

    private Long id;
    private String name;
    private EmployeeShortDto manager;

    public TeamInfoDto(Long id, String name, EmployeeShortDto manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public EmployeeShortDto getManager() { return manager; }

}
