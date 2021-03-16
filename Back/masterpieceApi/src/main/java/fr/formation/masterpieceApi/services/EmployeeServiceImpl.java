package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.config.EmployeeDetails;
import fr.formation.masterpieceApi.config.ResourceNotFoundException;
import fr.formation.masterpieceApi.dtos.*;
import fr.formation.masterpieceApi.entities.*;
import fr.formation.masterpieceApi.repositories.*;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeesRepo;
    private final DepartmentRepository departmentsRepo;
    private final TeamRepository teamsRepo;
    private final RoleRepository rolesRepo;
    private final PasswordEncoder passwordEncoder;
    private final ActivityRepository activitiesRepo;

    protected EmployeeServiceImpl(
            EmployeeRepository employeesRepo, DepartmentRepository departmentsRepo, TeamRepository teamsRepo,
            RoleRepository rolesRepo, PasswordEncoder passwordEncoder, ActivityRepository activitiesRepo) {
        this.employeesRepo = employeesRepo;
        this.departmentsRepo = departmentsRepo;
        this.teamsRepo = teamsRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
        this.activitiesRepo = activitiesRepo;
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

    @Override
    public boolean userExists(String username) {
        return employeesRepo.findByUsername(username).isPresent();
    }


    @Transactional
    @Override
    public void create(EmployeeCreateDto dto) {
        Role defaultRole = rolesRepo.findByDefaultRoleTrue().get();
        Set<Role> roles = new HashSet<Role>();
        Set<ListedActivities> listedActivities = new HashSet<ListedActivities>();
        roles.add(defaultRole);
        Employee employee = new Employee(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                roles);
        employee.setListedActivities(listedActivities);
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setDepartment(this.findOne(dto.getDepartment()));
        employee.setEmail(dto.getEmail());
        employee.setAccountNonExpired(true);
        employee.setAccountNonLocked(true);
        employee.setCredentialsNonExpired(true);
        System.out.println(employee.toString());
        employeesRepo.save(employee);
    }

    @Override
    public EmployeeActivitiesDto getOne(String username) { return employeesRepo.readByUsername(username); }

    @Override
    public List<EmployeeViewDto> getAll(Pageable pageable) { return employeesRepo.getAllProjectedBy(pageable); }

    @Override
    public List<EmployeeViewDto> getUserTeamMembers(String username) {
        Team team = employeesRepo.getByUsername(username).get().getTeam();
         return employeesRepo.getAllByTeam(team);
    }

    @Override
    public List<EmployeeActivitiesDto> getAllActivities(String username, String yearMonth) {
        List<EmployeeActivitiesDto> teamActivities = new ArrayList<>();
        List<EmployeeViewDto> teamMembers = getUserTeamMembers(username);
        for (EmployeeViewDto member : teamMembers) {
            teamActivities.add(getMonthActivities(member.getUsername(), yearMonth));
        }
        return teamActivities; //ByListedActivitiesActivityDateStartsWith(yearMonth);
    }

    @Override
    public EmployeeActivitiesDto getMonthActivities(String username, String yearMonth) {
/*        EmployeeActivitiesDto monthWithoutActivities =
                (EmployeeActivitiesDto) new Employee("username","",new HashSet<Role>(),true);
        monthWithoutActivities.setListedActivities(new HashSet<>());*/
        return employeesRepo.readByUsernameAndListedActivitiesActivityDateStartsWith(username, yearMonth);
        //.orElse(monthWithoutActivities);
        //.orElseThrow(() -> new ResourceNotFoundException("with username:" + username + " for " + yearMonth));
    }

    @Transactional
    @Override
    public void delete(String username) { employeesRepo.deleteByUsername(username); }

    // Throws ResourceNotFoundException (restful practice)
    @Override
    public Department findOne(String departmentName) {
        return departmentsRepo.findByName(departmentName).orElseThrow(
                () -> new ResourceNotFoundException("with name:" + departmentName));
    }

}
