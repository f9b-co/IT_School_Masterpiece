package fr.formation.masterpieceApi.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TaskInputDto {

    @NotBlank
    @Size(max = 64)
    private String name;
    @NotBlank
    @Size(max = 32)
    private String color;

    protected TaskInputDto() {
        // Empty no-arg constructor (Hibernate)
    }

    public String getName() { return name; }
    public String getColor() { return color; }

}
