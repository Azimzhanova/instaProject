package peaksoft.instaproject.validation.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.instaproject.validation.EmailValidation;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {EmailValidation.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailAnnValid {
    String message() default "email must contains '@'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
