package fr.formation.masterpieceApi.dtos.tmpDto;

public class TaskShortDto {

    private String name;
    private String color;

    public TaskShortDto(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() { return name; }
    public String getColor() { return color; }

}
