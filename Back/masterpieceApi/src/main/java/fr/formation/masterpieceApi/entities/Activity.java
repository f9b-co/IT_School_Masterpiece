package fr.formation.masterpieceApi.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="activities")
public class Activity extends AbstractEntity {

    @Column(name = "date", nullable = false, updatable = false)
    private String date;

    @Enumerated(EnumType.STRING)
    @Column(name = "half_day", nullable = false, updatable = false)
    private HalfDay halfDay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @OneToMany(mappedBy = "activity", fetch = FetchType.EAGER)
    private Set<ListedActivity> listedActivities;

    protected Activity() {
        // Empty no-arg constructor (Hibernate)
    }

    public Activity(String date, HalfDay halfDay, Task task) {
        this.date = date;
        this.halfDay = halfDay;
        this.task = task;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public HalfDay getHalfDay() { return halfDay; }
    public void setHalfDay(HalfDay halfDay) { this.halfDay = halfDay; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
    public Set<ListedActivity> getListedActivities() { return listedActivities; }
    public void setListedActivities(Set<ListedActivity> listedActivities) { this.listedActivities = listedActivities; }

}
