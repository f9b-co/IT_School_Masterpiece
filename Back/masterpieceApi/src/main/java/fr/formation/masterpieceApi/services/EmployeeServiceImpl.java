package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.config.EmployeeDetails;
import fr.formation.masterpieceApi.config.ResourceNotFoundException;
import fr.formation.masterpieceApi.dtos.*;
import fr.formation.masterpieceApi.entities.Role;
import fr.formation.masterpieceApi.entities.Employee;
import fr.formation.masterpieceApi.repositories.RoleRepository;
import fr.formation.masterpieceApi.repositories.EmployeeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeesRepo;
    private final RoleRepository rolesRepo;
    private final PasswordEncoder passwordEncoder;
    
    protected EmployeeServiceImpl(EmployeeRepository employeesRepo, RoleRepository rolesRepo, PasswordEncoder passwordEncoder) {
        this.employeesRepo = employeesRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Throws UsernameNotFoundException (Spring contract)
    @Override
    public EmployeeDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        EmployeeAuthDto user = employeesRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "no user found with username: " + username));
        return new EmployeeDetails(user);
    }

    // Throws ResourceNotFoundException (restful practice)
    @Override
    public EmployeeInfoDto getCurrentUserInfo(Long id) {
        return employeesRepo.getById(id).orElseThrow(
                () -> new ResourceNotFoundException("with id:" + id));
    }

    private void populateAndSave(@NotNull EmployeeDto dto, @NotNull Employee employee) {
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setDepartment(dto.getDepartment());
        employee.setEmail(dto.getEmail());
        employee.setUsername(dto.getUsername());
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employee.setEnable(true);
        Role defaultRole = rolesRepo.findByDefaultRoleTrue().get();
        HashSet<Role> roles = new HashSet<Role>();
        roles.add(defaultRole);
        employee.setRoles(roles);
        employeesRepo.save(employee);
    }

    @Override
    public boolean userExists(String username) {
        return employeesRepo.findByUsername(username).isPresent();
    }

    @Override
    public void create(EmployeeDto dto) {
        Employee employee = new Employee();
        populateAndSave(dto, employee);
        System.out.println(employee.toString());
    }

/*//to be activated later
    @Override
    public void changePassword(EmployeeChangePasswordDto dto) {
        Employee employee = employeesRepo.getByUsername(dto.getUsername()).get();
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employeesRepo.save(employee);
    }*/

    @Override
    public EmployeeViewDto getOne(String username) { return employeesRepo.readByUsername(username); }

    @Override
    public List<EmployeeViewDto> getAll(Pageable pageable) {
        return employeesRepo.getAllProjectedBy(pageable);
    }

    @Transactional
    @Override
    public void delete(String username) { employeesRepo.deleteByUsername(username); }

}
