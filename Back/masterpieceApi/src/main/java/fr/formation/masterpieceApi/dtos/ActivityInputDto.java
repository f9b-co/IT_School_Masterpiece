package fr.formation.masterpieceApi.dtos;

import fr.formation.masterpieceApi.entities.HalfDay;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ActivityInputDto {

    @NotBlank
    @Size(min = 10, max = 10)
    private String date;

    @NotNull
    private HalfDay halfDay;

    @NotNull
    private TaskInputDto task;

    protected ActivityInputDto() {
        // Empty no-arg constructor (Hibernate)
    }

    public String getDate() { return date; }
    public HalfDay getHalfDay() { return halfDay; }
    public TaskInputDto getTask() { return task; }

}
