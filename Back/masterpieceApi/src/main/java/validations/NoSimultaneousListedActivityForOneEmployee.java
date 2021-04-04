package validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
 * Custom annotation to ensure no simultaneous ListedActivity for one Employee
 * Used on creation
 */

@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = NoSimultaneousListedActivityForOneEmployeeValidator.class)
public @interface NoSimultaneousListedActivityForOneEmployee {

    String message() default "activité déclarée pour le même employee et la même demi-journée déjà existante.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
