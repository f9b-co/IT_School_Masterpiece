package fr.formation.masterpieceApi.entities;

import fr.formation.masterpieceApi.utilities.BooleanConverter;

import javax.persistence.*;


@Entity
@Table(name="listed_activities")
public class ListedActivity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @Convert(converter = BooleanConverter.class)
    @Column(length = 1, nullable = false)
    private  boolean validated;

    protected ListedActivity() {
        // Empty no-arg constructor (Hibernate)
    }

    public ListedActivity(Employee employee, Activity activity, boolean validated) {
        this.employee = employee;
        this.activity = activity;
        this.validated = validated;
    }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public Activity getActivity() { return activity; }
    public void setActivity(Activity activity) { this.activity = activity; }
    public boolean isValidated() { return validated; }
    public void setValidated(boolean validated) { this.validated = validated; }

}
