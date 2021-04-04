package validations;

import fr.formation.masterpieceApi.dtos.in.ListedActivityCreateDto;
import fr.formation.masterpieceApi.entities.Employee;
import fr.formation.masterpieceApi.services.EmployeeService;
import fr.formation.masterpieceApi.services.ListedActivityService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * Validator for NoSimultaneousListedActivityForOneEmployee constraint
 * Check if simultaneous ListedActivity for one Employee already exists
 * returns a boolean
 */
public class NoSimultaneousListedActivityForOneEmployeeValidator
        implements ConstraintValidator<NoSimultaneousListedActivityForOneEmployee, ListedActivityCreateDto> {

    private final ListedActivityService listedActivityService;
    private final EmployeeService employeeService;

    public NoSimultaneousListedActivityForOneEmployeeValidator(
            ListedActivityService listedActivityService, EmployeeService employeeService) {
        this.listedActivityService = listedActivityService;
        this.employeeService = employeeService;
    }

    @Override
    public boolean isValid(ListedActivityCreateDto dto, ConstraintValidatorContext context) {
        Employee employee = employeeService.getOneById(dto.getEmployeeId());
        return !listedActivityService.simultaneousListedActivityForOneEmployeeExists(
                employee, dto.getActivity().getDate(), dto.getActivity().getHalfDay());
    }
}
