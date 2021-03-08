package fr.formation.masterpieceApi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tasks")
public class Task extends AbstractEntity {

    @Column(length = 64, nullable = false)
    private String name;
    @Column(length = 5, nullable = false, unique = true)
    private String code;

    protected Task() {
        // Empty no-arg constructor (Hibernate)
    }

    public Task(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

}
