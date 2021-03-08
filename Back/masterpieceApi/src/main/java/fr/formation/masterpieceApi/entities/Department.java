package fr.formation.masterpieceApi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="departments")
public class Department extends AbstractEntity {

    @Column(length = 64, nullable = false)
    private String name;
    @Column(precision = 10, nullable = false, unique = true)
    private int code;

    protected Department() {
        // Empty no-arg constructor (Hibernate)
    }

    public Department(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
}
