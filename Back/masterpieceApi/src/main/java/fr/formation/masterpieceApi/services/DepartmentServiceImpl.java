package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.entities.Department;
import fr.formation.masterpieceApi.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentsRepo;

    public DepartmentServiceImpl(DepartmentRepository departmentsRepo) {
        this.departmentsRepo = departmentsRepo;
    }

    @Override
    public List<Department> findAll() {
        return departmentsRepo.findAll();
    }

}
