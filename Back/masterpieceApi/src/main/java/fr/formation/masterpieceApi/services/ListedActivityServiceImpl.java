package fr.formation.masterpieceApi.services;

import fr.formation.masterpieceApi.dtos.ActivityInputDto;
import fr.formation.masterpieceApi.dtos.ListedActivityInputDto;
import fr.formation.masterpieceApi.entities.Activity;
import fr.formation.masterpieceApi.entities.Employee;
import fr.formation.masterpieceApi.entities.ListedActivity;
import fr.formation.masterpieceApi.entities.Task;
import fr.formation.masterpieceApi.repositories.ActivityRepository;
import fr.formation.masterpieceApi.repositories.EmployeeRepository;
import fr.formation.masterpieceApi.repositories.ListedActivityRepository;
import fr.formation.masterpieceApi.repositories.TaskRepository;
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

    @Transactional /* delete if exists, any old listedActivity on the same date & halfDay */
    private ListedActivity findOrCreateListedActivity(Employee employee, Activity activity) {
        try {
            List<ListedActivity> oldSameHalfDay =
                    listedActivitiesRepo.findByEmployeeAndActivityDateAndActivityHalfDay(
                    employee, activity.getDate(), activity.getHalfDay());
            oldSameHalfDay.forEach(oldLA -> { listedActivitiesRepo.deleteById(oldLA.getId()); });
            return listedActivitiesRepo.findByEmployeeAndActivity(employee, activity).get();
        } catch (final NoSuchElementException ex) {
            ListedActivity newLA = new ListedActivity(employee, activity, false);
            listedActivitiesRepo.save(newLA);
            return newLA;
        }
    }

    @Transactional
    @Override
    public void patchAllListed(List<ListedActivityInputDto> dtoList) {
        dtoList.forEach(dto -> {
            Employee employee = employeesRepo.getOne(dto.getEmployeeId());
            Activity activity = findOrCreateActivity(dto.getActivity());
            ListedActivity listedActivity = findOrCreateListedActivity(employee, activity);
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
