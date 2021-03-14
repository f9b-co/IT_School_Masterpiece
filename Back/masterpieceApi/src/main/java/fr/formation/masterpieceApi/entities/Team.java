package fr.formation.masterpieceApi.entities;

import javax.persistence.*;

@Entity
@Table(name="teams")
public class Team extends AbstractEntity {

    @Column(length = 64, nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    protected Team() {
        // Empty no-arg constructor (Hibernate)
    }

    public Team(String name, Employee manager) {
        this.name = name;
        this.manager = manager;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Employee getManager() { return manager; }
    public void setManager(Employee manager) { this.manager = manager; }
}
