package validations;

import fr.formation.masterpieceApi.services.EmployeeService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * Validator for UniqueEmail constraint
 * Check if email is already in use
 * returns a boolean
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final EmployeeService service;

    public UniqueEmailValidator(EmployeeService service) {
	this.service = service;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
	    return !service.emailExists(email);
    }
}
