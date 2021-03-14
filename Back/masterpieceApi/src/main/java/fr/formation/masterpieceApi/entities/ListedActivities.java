package fr.formation.masterpieceApi.entities;

import fr.formation.masterpieceApi.utilities.BooleanConverter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="listed_activities")
@IdClass(ListedActivitiesId.class)
public class ListedActivities implements Serializable {

    @Id
    @Column(name = "employee_id", insertable = false, updatable = false)
    private Long employeeId;

    @Id
    @Column(name = "activity_id", insertable = false, updatable = false)
    private Long activityId;

    @Convert(converter = BooleanConverter.class)
    @Column(length = 1, nullable = false)
    private  boolean validated;

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }
    public boolean isValidated() { return validated; }
    public void setValidated(boolean validated) { this.validated = validated; }
}
