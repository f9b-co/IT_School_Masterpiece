package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.config.EmployeeDetails;
import fr.formation.masterpieceApi.exceptions.ResourceNotFoundException;
import fr.formation.masterpieceApi.dtos.*;
import fr.formation.masterpieceApi.entities.*;
import fr.formation.masterpieceApi.repositories.*;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeesRepo;
    private final DepartmentRepository departmentsRepo;
    private final TeamRepository teamsRepo;
    private final RoleRepository rolesRepo;
    private final PasswordEncoder passwordEncoder;

    protected EmployeeServiceImpl(
            EmployeeRepository employeesRepo, DepartmentRepository departmentsRepo, TeamRepository teamsRepo,
            RoleRepository rolesRepo, PasswordEncoder passwordEncoder) {
        this.employeesRepo = employeesRepo;
        this.departmentsRepo = departmentsRepo;
        this.teamsRepo = teamsRepo;
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

    @Override
    public boolean usernameExists(String username) {
        return employeesRepo.findByUsername(username).isPresent();
    }

    @Override
    public boolean emailExists(String email) { return employeesRepo.findByEmail(email).isPresent(); }

    public boolean isManager(String username) {
        return employeesRepo.findByUsername(username).get()
                .getRoles().stream().anyMatch(role -> role.getName().matches("Manager"));
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
        employee.setTeam(teamsRepo.findById((long) 1).get());
        employee.setDepartment(departmentsRepo.findByName(dto.getDepartment()).get());
        employee.setEmail(dto.getEmail());
        employee.setAccountNonExpired(true);
        employee.setAccountNonLocked(true);
        employee.setCredentialsNonExpired(true);
        System.out.println(employee.toString());
        employeesRepo.save(employee);
    }

    @Override
    public EmployeeViewDto getOne(String username) { return employeesRepo.readByUsername(username).orElseThrow(
            () -> new ResourceNotFoundException("with username:" + username)); }

    @Override
    public List<EmployeeInfoDto> getAll(Pageable pageable) { return employeesRepo.getAllProjectedBy(pageable); }

    private List<EmployeeShortDto> getTeamMembers(String teamName) {
        Team team = teamsRepo.findByName(teamName).get();
        return employeesRepo.getAllDistinctByTeam(team);
    }

    @Override
    public List<EmployeeShortDto> getUserTeamMembers(String username) {
        String teamName = getOne(username).getTeam().getName();
        List<EmployeeShortDto> teamsMembers = getTeamMembers(teamName);
        if (teamName.equals("hors équipes")) {
            teamsMembers = teamsMembers.stream()
                    .filter(m -> m.getUsername().matches(username)).collect(Collectors.toList());//.collect(Collectors.toList())
            System.out.println(teamsMembers);
        }
        return teamsMembers;
    }

    @Override
    public List<List<EmployeeShortDto>> getManagerTeamsMembers(Long managerId) {
        List<TeamShortDto> teams = teamsRepo.findAllByManagerId(managerId);
        List<List<EmployeeShortDto>> teamsMembers = new ArrayList<>();
        for (TeamShortDto team: teams) {
                teamsMembers.add(getTeamMembers(team.getName()));
        }
        return teamsMembers;
    }

    @Override
    public List<EmployeeActivitiesDto> getTeamMonthActivities(String teamName, String yearMonth, String username) {
        List<EmployeeActivitiesDto> teamActivities = new ArrayList<>();
        if (teamName.equals("hors équipes") && !isManager(username)){
            teamActivities.add(getUserMonthActivities(username, yearMonth));
        } else {
            List<EmployeeShortDto> teamMembers = getTeamMembers(teamName);
            for (EmployeeShortDto member : teamMembers) {
                teamActivities.add(getUserMonthActivities(member.getUsername(), yearMonth));
            }
        }
        return teamActivities;
    }

    private EmployeeActivitiesDto getUserMonthActivities(String username, String yearMonth) {
        return employeesRepo.readByUsernameAndListedActivitiesActivityDateStartsWith(username, yearMonth);
    }

    @Transactional
    @Override
    public void delete(String username) { employeesRepo.deleteByUsername(username); }

}
