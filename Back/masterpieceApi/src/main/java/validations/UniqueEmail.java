package validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
 * Custom annotation to ensure employee's email is unique
 */

@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

    String message() default "Courriel déjà utilisé";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
