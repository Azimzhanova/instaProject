package peaksoft.instaproject.validation.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.instaproject.validation.PasswordValidation;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {PasswordValidation.class} //указываем класс валидатора
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordAnnValid { // @PasswordAnnValid - вызываем в request, потому что валидации запросы до entity должен держать в request. Но нашу аннотацию-валидацию можно использовать в любом классе
    String message() default "password must be at least 6 characters and contains one uppercase letter"; //default message

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
