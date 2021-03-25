package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.exceptions.ResourceNotFoundException;
import fr.formation.masterpieceApi.entities.Department;
import fr.formation.masterpieceApi.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl extends AbstractService implements DepartmentService {

    private final DepartmentRepository departmentsRepo;

    public DepartmentServiceImpl(DepartmentRepository departmentsRepo) {
        this.departmentsRepo = departmentsRepo;
    }

    // Throws ResourceNotFoundException (restful practice)
    @Override
    public Department findOne(String departmentName) {
        return departmentsRepo.findByName(departmentName).orElseThrow(
                () -> new ResourceNotFoundException("with name:" + departmentName));
    }

    @Override
    public List<Department> findAll() {
        return departmentsRepo.findAll();
    }

}
