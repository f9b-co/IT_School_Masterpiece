package validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/*
 * Custom annotation to ensure employee's username is unique
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {

    String message() default "Username already used";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
