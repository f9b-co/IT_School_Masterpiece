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
    @Column(length = 32, nullable = false, unique = true)
    private String color;

    protected Task() {
        // Empty no-arg constructor (Hibernate)
    }

    public Task(String name, String code, String color) {
        this.name = name;
        this.code = code;
        this.color = color;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

}
