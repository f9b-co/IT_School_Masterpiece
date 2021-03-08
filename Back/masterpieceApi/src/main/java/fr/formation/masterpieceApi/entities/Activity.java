package fr.formation.masterpieceApi.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="activities")
public class Activity extends AbstractEntity {

    @Column(name = "date", updatable = false)
    private LocalDate activityDate;

    @Column(name = "half_day", updatable = false)
    private String halfDay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;

    protected Activity() {
        // Empty no-arg constructor (Hibernate)
    }

    public Activity(LocalDate activityDate, String halfDay, Task task) {
        this.activityDate = activityDate;
        this.halfDay = halfDay;
        this.task = task;
    }

    public LocalDate getActivityDate() { return activityDate; }
    public void setActivityDate(LocalDate activityDate) { this.activityDate = activityDate; }
    public String getHalfDay() { return halfDay; }
    public void setHalfDay(String halfDay) { this.halfDay = halfDay; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
}
