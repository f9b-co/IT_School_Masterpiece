package fr.formation.masterpieceApi.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="activities")
public class Activity extends AbstractEntity {

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate activityDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "half_day", nullable = false, updatable = false)
    private HalfDay halfDay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @OneToMany(mappedBy = "activity", fetch = FetchType.EAGER)
    private Set<ListedActivities> listedActivities;

    protected Activity() {
        // Empty no-arg constructor (Hibernate)
    }

    public Activity(LocalDate activityDate, HalfDay halfDay, Task task) {
        this.activityDate = activityDate;
        this.halfDay = halfDay;
        this.task = task;
    }

    public LocalDate getActivityDate() { return activityDate; }
    public void setActivityDate(LocalDate activityDate) { this.activityDate = activityDate; }
    public HalfDay getHalfDay() { return halfDay; }
    public void setHalfDay(HalfDay halfDay) { this.halfDay = halfDay; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
    public Set<ListedActivities> getListedActivities() { return listedActivities; }
    public void setListedActivities(Set<ListedActivities> listedActivities) { this.listedActivities = listedActivities; }

}
