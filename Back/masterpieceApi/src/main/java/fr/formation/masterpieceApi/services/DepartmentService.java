package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.entities.Department;

import java.util.List;

public interface DepartmentService {

    Department findOne(String DepartmentName);
    List<Department> findAll();

}
