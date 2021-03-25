package validations;

import fr.formation.masterpieceApi.services.EmployeeService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * Validator for UniqueUsername constraint
 */
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private EmployeeService service;

    public UniqueUsernameValidator(EmployeeService service) {
	this.service = service;
    }

    /*
     * Validator for UniqueUsername constraint
     * Check if username is already in use
     * returns a boolean
     */
    @Override
    public boolean isValid(String username,ConstraintValidatorContext context) {
	    return service.usernameExists(username);
    }
}