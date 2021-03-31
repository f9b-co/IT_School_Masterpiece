package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.ActivityInputDto;
import fr.formation.masterpieceApi.dtos.ListedActivityInputDto;
import fr.formation.masterpieceApi.entities.*;
import fr.formation.masterpieceApi.repositories.ActivityRepository;
import fr.formation.masterpieceApi.repositories.EmployeeRepository;
import fr.formation.masterpieceApi.repositories.ListedActivityRepository;
import fr.formation.masterpieceApi.repositories.TaskRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ListedActivityServiceImpl implements ListedActivityService {

    private final  EmployeeRepository employeesRepo;
    private final ActivityRepository activitiesRepo;
    private final TaskRepository tasksRepo;
    private final ListedActivityRepository listedActivitiesRepo;

    public ListedActivityServiceImpl(EmployeeRepository employeesRepo, ActivityRepository activityRepo,
                                     TaskRepository tasksRepo, ListedActivityRepository listedActivitiesRepo) {
        this.employeesRepo = employeesRepo;
        this.activitiesRepo = activityRepo;
        this.tasksRepo = tasksRepo;
        this.listedActivitiesRepo = listedActivitiesRepo;
    }

    @Transactional
    private Activity findOrCreateActivity(ActivityInputDto dto) {
        Task task = tasksRepo.findByColor(dto.getTask().getColor()).get();
        try {
            return activitiesRepo.findByDateAndHalfDayAndTask(dto.getDate(), dto.getHalfDay(), task).get();
        } catch (final NoSuchElementException ex) {
            Activity newActivity = new Activity(dto.getDate(), dto.getHalfDay(), task);
            activitiesRepo.save(newActivity);
            return newActivity;
        }
    }

    private boolean listedActivityExists(Employee employee, Activity activity) {
        return listedActivitiesRepo.findByEmployeeAndActivity(employee, activity).isPresent();
    }

    @Transactional
    @Override
    public void createAllListed(List<ListedActivityInputDto> dtoList) {
        dtoList.forEach(dto -> {
            Employee employee = employeesRepo.getOne(dto.getEmployeeId());
            Activity activity = findOrCreateActivity(dto.getActivity());
            ListedActivity newLA = new ListedActivity(employee, activity, dto.isValidated());
            listedActivitiesRepo.save(newLA);
        });
    }

    @Transactional
    @Override
    public void patchAllListed(List<ListedActivityInputDto> dtoList) {
        dtoList.forEach(dto -> {
            Employee employee = employeesRepo.getOne(dto.getEmployeeId());
            Activity activity = findOrCreateActivity(dto.getActivity());
            ListedActivity listedActivity = listedActivitiesRepo.findByEmployeeAndActivityDateAndActivityHalfDay(
                    employee, dto.getActivity().getDate(), dto.getActivity().getHalfDay()).get();;
            listedActivity.setActivity(activity);
            listedActivity.setValidated(dto.isValidated());
            listedActivitiesRepo.save(listedActivity);
        });
    }

    @Override
    public void delete(Long id) {
    //todo?
    }

}
