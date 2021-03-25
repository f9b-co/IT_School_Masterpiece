package fr.formation.masterpieceApi.dtos.tmpDto;

import fr.formation.masterpieceApi.entities.HalfDay;

public class ActivityViewDto {

    private String date;
    private HalfDay halfDay;
    private TaskShortDto task;

    public ActivityViewDto(String date, HalfDay halfDay, TaskShortDto task) {
        this.date = date;
        this.halfDay = halfDay;
        this.task = task;
    }

    public String getDate() { return date; }
    public HalfDay getHalfDay() { return halfDay; }
    public TaskShortDto getTask() { return task; }

}
