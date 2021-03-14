package fr.formation.masterpieceApi.entities;

import java.io.Serializable;

public class ListedActivitiesId implements Serializable {

    private Long employeeId;

    private Long activityId;

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }

}
